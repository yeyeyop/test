package com.icia.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.icia.web.model.HiBoard;
import com.icia.web.model.HiBoardFile;

@Repository("hiBoardDao")
public interface HiBoardDao 
{
   //게시물 총수
   public long boardListCount(HiBoard hiBoard);  //인터페이스라서 다들 선언부밖에 없어욤
   
   //게시물 조회
   public List<HiBoard> boardList(HiBoard hiBoard);
   
   //게시물 등록
   public int boardInsert(HiBoard hiBoard);     //HiBoardDao.xml 가서 id값과 이름을 똑같이 맞추기
   
   //게시물 답글 등록
   public int boardReplyInsert(HiBoard hiBoard);
   
   //게시물 조회
   public HiBoard boardSelect(long hiBbsSeq);    //long타입으로 hiBbsSeq를 받음
   
   //게시물 수정
   public int boardUpdate(HiBoard hiBoard);
   
   //게시물 그룹 순서 변경
   public int boardGroupOrderUpdate(HiBoard hiBoard);
  
   //게시물 조회수 증가
   public int boardReadCntPlus(long hiBbsSeq);
   
   //게시물 삭제
   public int boardDelete(long hiBbsSeq);
   
   //게시물 첨부파일 등록
   public int boardFileInsert(HiBoardFile hiBoardFile);
   
   //게시물 첨부파일 조회
   public HiBoardFile boardFileSelect(long hiBbsSeq);
   
   //게시물 첨부파일 삭제
   public int boardFileDelete(long hiBbsSeq);
   
   //게시물 삭제시 답변글 수 체크
   public int boardAnswersCount(long hiBbsSwq);
}