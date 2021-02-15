package com.icia.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.icia.common.model.FileData;
import com.icia.common.util.StringUtil;
import com.icia.web.model.HiBoard;
import com.icia.web.model.HiBoardFile;
import com.icia.web.model.Paging;
import com.icia.web.model.Response;
import com.icia.web.model.User;
import com.icia.web.service.HiBoardService;
import com.icia.web.service.UserService;
import com.icia.web.util.CookieUtil;
import com.icia.web.util.HttpUtil;
import com.icia.web.util.JsonUtil;

@Controller("hiBoardController")
public class HiBoardController 
{
	private static Logger logger = LoggerFactory.getLogger(HiBoardController.class);

	//쿠키명
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	//파일경로
	@Value("#{env['upload.save.dir']}")
	private String UPLOAD_SAVE_DIR;
	
	@Autowired
	private HiBoardService hiBoardService;
	
	@Autowired
	private UserService userService;
	
	private static final int LIST_COUNT = 10;
	private static final int PAGE_COUNT = 10;
	
	//게시물 수정
	@RequestMapping(value="/board/updateProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> updateProc(MultipartHttpServletRequest request, HttpServletResponse response)
	{
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		long hiBbsSeq = HttpUtil.get(request, "hiBbsSeq", (long)0);
		String hiBbsTitle = HttpUtil.get(request, "hiBbsTitle", "");
	    String hiBbsContent = HttpUtil.get(request, "hiBbsContent", "");
	    FileData fileData = HttpUtil.getFile(request, "hiBbsFile", UPLOAD_SAVE_DIR);		
	    
	    Response<Object> ajaxResponse = new Response<Object>();
	    
	    if(hiBbsSeq > 0 && !StringUtil.isEmpty(hiBbsTitle) && !StringUtil.isEmpty(hiBbsContent))
	    {
	    	HiBoard hiBoard = hiBoardService.boardSelect(hiBbsSeq);
	    	
	    	if(hiBoard != null)
	    	{
	    		if(StringUtil.equals(hiBoard.getUserId(), cookieUserId))
	    		{
	    			hiBoard.setHiBbsSeq(hiBbsSeq);
	    			hiBoard.setHiBbsTitle(hiBbsTitle);
	    			hiBoard.setHiBbsContent(hiBbsContent);
	    			
	    			if(fileData != null && fileData.getFileSize() > 0)
	    			{
	    				HiBoardFile hiBoardFile = new HiBoardFile();
	    				hiBoardFile.setFileName(fileData.getFileName());
	    				hiBoardFile.setFileOrgName(fileData.getFileOrgName());
	    				hiBoardFile.setFileExt(fileData.getFileExt());
	    				hiBoardFile.setFileSize(fileData.getFileSize());
	    				
	    				hiBoard.setHiBoardFile(hiBoardFile);
	    			}
	    			
	    			try
	    			{
	    				if(hiBoardService.boardUpdate(hiBoard) > 0)
	    				{
	    					ajaxResponse.setResponse(0, "Success");
	    				}
	    				else
	    				{
	    					ajaxResponse.setResponse(500, "Internal Server Error");
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				logger.error("[HiBoardController] /board/updateProc Exception", e);
	    				ajaxResponse.setResponse(500, "Internal Server Error");
	    			}
	    		}
	    		else
	    		{
	    			ajaxResponse.setResponse(404, "Not Found");
	    		}
	    	}
	    	else
	    	{
	    		ajaxResponse.setResponse(404, "Not Found");
	    	}
	    }
	    else
	    {
	    	ajaxResponse.setResponse(400, "Bad Request");
	    }
	    
	    if(logger.isDebugEnabled())
	    {
	       logger.debug("[HiBoardController] /board/updateProc response\n" + JsonUtil.toJsonPretty(ajaxResponse));
	    }
	    
	    return ajaxResponse;
	}
	//게시물 수정 폼
	@RequestMapping(value="/board/updateForm")
	public String updateForm(ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		long hiBbsSeq = HttpUtil.get(request, "hiBbsSeq", (long)0);
		String searchType = HttpUtil.get(request, "searchType", "");
		String searchValue = HttpUtil.get(request, "searchValue", "");
		long curPage = HttpUtil.get(request, "curPage", (long)1);
		
		HiBoard hiBoard = null;
		User user = null;
		
		if(hiBbsSeq > 0)
		{
			hiBoard = hiBoardService.boardView(hiBbsSeq);
			
			if(hiBoard != null)
			{
				if(StringUtil.equals(hiBoard.getUserId(), cookieUserId))
				{
					user = userService.userSelect(cookieUserId);
				}
				else
				{
					hiBoard = null;
				}
			}
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("hiBoard", hiBoard);
		model.addAttribute("user", user);
		
		return "/board/updateForm";
	}

	
	//게시물 답변
	@RequestMapping(value="/board/replyProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> replyProc(MultipartHttpServletRequest request, HttpServletResponse response)
	{                    //multi~: 파일 업로드할 때마다 매개변수 앞에 넣어주는 것 리퀘스트에서만 붙이는 것 
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		long hiBbsSeq = HttpUtil.get(request, "hiBbsSeq", (long)0);
		String hiBbsTitle = HttpUtil.get(request, "hiBbsTitle", "");
		String hiBbsContent = HttpUtil.get(request, "hiBbsContent", "");
		
		FileData fileData = HttpUtil.getFile(request, "hiBbsFile", UPLOAD_SAVE_DIR);
		Response<Object> ajaxResponse = new Response<Object>();
		
		if(hiBbsSeq > 0 && !StringUtil.isEmpty(hiBbsTitle) && !StringUtil.isEmpty(hiBbsContent))//넘어온 값이 정상적으로 넘어왔나 체크
		{
			HiBoard parentHiBoard = hiBoardService.boardSelect(hiBbsSeq); //댓글이 부모(게시물) 아래에 와야 하니까 
			
			if(parentHiBoard != null)
			{
				HiBoard hiBoard = new HiBoard();
				
				hiBoard.setUserId(cookieUserId);
				hiBoard.setHiBbsTitle(hiBbsTitle);
				hiBoard.setHiBbsContent(hiBbsContent);
				hiBoard.setHiBbsGroup(parentHiBoard.getHiBbsGroup());     
				hiBoard.setHiBbsOrder(parentHiBoard.getHiBbsOrder() + 1);  //부모는 0 
				hiBoard.setHiBbsIndent(parentHiBoard.getHiBbsIndent() + 1);  //댓글의 댓글(indent 1은 첫번쨰 댓글의, 2는 두번쨰 댓글의 댓글)
				hiBoard.setHiBbsParent(hiBbsSeq);
				
				if(fileData != null && fileData.getFileSize() >0)
				{
					HiBoardFile hiBoardFile = new HiBoardFile();
					
					hiBoardFile.setFileName(fileData.getFileName());
					hiBoardFile.setFileOrgName(fileData.getFileOrgName());
					hiBoardFile.setFileExt(fileData.getFileExt());
					hiBoardFile.setFileSize(fileData.getFileSize());
					
					hiBoard.setHiBoardFile(hiBoardFile);
				}
				
				try
				{
					if(hiBoardService.boardReplyInsert(hiBoard) > 0)
					{
						ajaxResponse.setResponse(0, "Success");
					}
					else
					{
						ajaxResponse.setResponse(500, "Internal Server Error");
					}
				}
				catch(Exception e)
				{
					logger.error("[HiBoardController] /board/replyProc Exception", e);
					ajaxResponse.setResponse(500, "Internal Server Error");
				}
			}
			else
			{
				ajaxResponse.setResponse(404, "Not Found");
			}
		}
		else
		{
			ajaxResponse.setResponse(400, "Bad Request");
		}
		
	    if(logger.isDebugEnabled())
	    {
	       logger.debug("[HiBoardController] /board/replyProc response\n" + JsonUtil.toJsonPretty(ajaxResponse));
	    }
	      
		return ajaxResponse;
	}
	
	//게시물 답변 폼
	@RequestMapping(value="/board/replyForm", method=RequestMethod.POST)
	public String replyForm(ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{						//ModelMap : 이 매개변수를 가지는 건 리스폰스바디가 어노테이션 안 붙고 .jsp로 조합하자나 그 때 
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		long hiBbsSeq = HttpUtil.get(request, "hiBbsSeq", (long)0);
		
		String searchType = HttpUtil.get(request, "searchType");
		String searchValue = HttpUtil.get(request, "searchValue");
		long curPage = HttpUtil.get(request, "curPage", (long)1);
		
		HiBoard hiBoard = null;
		User user = null;
		
		if(hiBbsSeq > 0)
		{
			hiBoard = hiBoardService.boardSelect(hiBbsSeq);
			
			if(hiBoard != null)
			{
				user = userService.userSelect(cookieUserId);
			}
			
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("hiBoard", hiBoard);
		model.addAttribute("user", user);
		
		return "/board/replyForm";
	}
	//게시물 삭제
	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> delete(HttpServletRequest request, HttpServletResponse response)
	{
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		long hiBbsSeq = HttpUtil.get(request, "hiBbsSeq", (long)0);
		
		Response<Object> ajaxResponse = new Response<Object>();
		
		if(hiBbsSeq > 0)
		{
			HiBoard hiBoard = hiBoardService.boardSelect(hiBbsSeq);
			
			if(hiBoard != null) //게시판의 글을 가지고 온 것
			{
				if(StringUtil.equals(hiBoard.getUserId(), cookieUserId)) //현재 로그인한 아이디와 게시판에 등록되어있는 아이디가 같냐
				{
					try
					{
						if(hiBoardService.boardAnswersCount(hiBoard.getHiBbsSeq()) > 0)  //값이 존재한다 - 내 부모가 있다 - 삭제하면안돼
						{
							ajaxResponse.setResponse(-999, "Answers exist and cannot be delete");
						}
						else
						{
							if(hiBoardService.boardDelete(hiBoard.getHiBbsSeq()) > 0)
							{
								ajaxResponse.setResponse(0, "Success");
							}
							else
							{
								ajaxResponse.setResponse(500, "Internal Server Error");
							}
						}
					}
					catch(Exception e)
					{
						logger.error("[HiBoardController] /board/delete Exception", e);
						ajaxResponse.setResponse(500, "Internal Server Error");
					}
				}
				else  //getUserId랑 cookieUserId가 다를 때
				{
					ajaxResponse.setResponse(400, "Not Found");
				}
			}
			else
			{
				ajaxResponse.setResponse(400, "Not Found");
			}
		}
		else
		{
			ajaxResponse.setResponse(400, "Bad Request");
		}

	      if(logger.isDebugEnabled())
	      {
	         logger.debug("[HiBoardController] /board/delete response\n" + JsonUtil.toJsonPretty(ajaxResponse));
	      }
		
		return ajaxResponse;
	}
	
	//게시물 조회
	@RequestMapping(value="/board/view")
	public String view(ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		long hiBbsSeq = HttpUtil.get(request, "hiBbsSeq", (long)0);
		String searchType = HttpUtil.get(request, "searchType");
		String searchValue = HttpUtil.get(request, "searchValue", "");
		long curPage = HttpUtil.get(request, "curPage", (long)1);
		//본인글 확인 여부
		String boardMe = "N";
		
		HiBoard hiBoard = null;
		
		if(hiBbsSeq > 0)
		{
			hiBoard = hiBoardService.boardView(hiBbsSeq);
			
			if(hiBoard != null && StringUtil.equals(hiBoard.getUserId(), cookieUserId))
			{
				boardMe = "Y";   //본인글인경우
			}	
		}
		
		model.addAttribute("hiBbsSeq", hiBbsSeq);
		model.addAttribute("hiBoard", hiBoard);
		model.addAttribute("boardMe", boardMe);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		
		return "/board/view";
	}
	
	//게시물 쓰기
	@RequestMapping(value="/board/writeForm")
	public String writeForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) //방식이 따로 안 적혀있다면 알아서 판단함
	{
		//쿠키값
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		String searchType = HttpUtil.get(request, "searchType");
		String searchValue = HttpUtil.get(request, "searchValue");
		long curPage = HttpUtil.get(request, "curPage", (long)1);
		
		//사용자 정보 조회
		User user = userService.userSelect(cookieUserId);
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("user", user);
		
		return "/board/writeForm";
	}
	
	//게시물 등록
	@RequestMapping(value="/board/writeProc", method=RequestMethod.POST)
	@ResponseBody //ajax통신에 대한 리턴이다
	public Response<Object> writeProc(MultipartHttpServletRequest request, HttpServletResponse response)
	{
		String cookieUserid = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		String hiBbsTitle = HttpUtil.get(request, "hiBbsTitle", "");
		String hiBbsContent = HttpUtil.get(request, "hiBbsContent", "");
		FileData fileData = HttpUtil.getFile(request, "hiBbsFile", UPLOAD_SAVE_DIR);
		
		Response<Object> ajaxResponse = new Response<Object>();
		
		if(!StringUtil.isEmpty(hiBbsTitle) && !StringUtil.isEmpty(hiBbsContent))
		{
			HiBoard hiBoard = new HiBoard();
			
			hiBoard.setUserId(cookieUserid);
			hiBoard.setHiBbsTitle(hiBbsTitle);
			hiBoard.setHiBbsContent(hiBbsContent);
			
			if(fileData !=null && fileData.getFileSize() >0)
			{
				HiBoardFile hiBoardFile = new HiBoardFile();
				
				hiBoardFile.setFileName(fileData.getFileName());
				hiBoardFile.setFileOrgName(fileData.getFileOrgName());
				hiBoardFile.setFileExt(fileData.getFileExt());
				hiBoardFile.setFileSize(fileData.getFileSize());
				
				hiBoard.setHiBoardFile(hiBoardFile); //파일에 대한 값을 하이보드에 담기
			}
			
			try
			{					//메소드예외처리 되어있음(서비스 단에서)
				if(hiBoardService.boardInsert(hiBoard) >0)
				{
					ajaxResponse.setResponse(0, "Success");
				}
				else
				{
					ajaxResponse.setResponse(500, "Internal Server Error");
				}
			}
			catch(Exception e)
			{
				logger.error("[HiBoardController]/board/writeProc Exception", e);
				ajaxResponse.setResponse(500, "Internal Server Error"); //메세지에는 인터널어쩌구 넘기기
			}
		}
		else //title이나 content 둘 중 하나가 비어있을 때
		{
			ajaxResponse.setResponse(400, "Bad Request"); 
		}

		if(logger.isDebugEnabled())
	    {
	       logger.debug("[HiBoardController] /board/writeProc response\n" + JsonUtil.toJsonPretty(ajaxResponse));
	    }
		
		return ajaxResponse;
				
	}
	
	//게시물 리스트 
	@RequestMapping(value="/board/list")
	public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		//조회항목(1:작성자조회, 2:제목조회, 3:내용조회)
		String searchType = HttpUtil.get(request, "searchType");
		//조회값
		String searchValue = HttpUtil.get(request, "searchValue");
		//현재 페이지
		long curPage = HttpUtil.get(request, "curPage", (long)1);
		//총 게시물 수
		long totalCount =0;
		//게시물 리스트
		List<HiBoard> list= null;
		//페이징 객체
		Paging paging = null;
		//조회객체
		HiBoard search = new HiBoard();
			
		if(!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue))
		{
			search.setSearchType(searchType);
			search.setSearchValue(searchValue);
		}
		else
		{
			searchType = "";
			searchValue = "";
		}
		
		totalCount = hiBoardService.boardListCount(search);
		
		logger.debug("totalCount : " + totalCount);
		
		if(totalCount > 0)
		{
			paging = new Paging("/board/list", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
	
			paging.addParam("searchType", searchValue);
			paging.addParam("searchValue", searchValue);
			paging.addParam("curPage", curPage);
			
			search.setStartRow(paging.getStartRow());
			search.setEndRow(paging.getEndRow());
			
			list = hiBoardService.boardList(search);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("paging", paging);
		return "/board/list";
	}
	
	
	
}
