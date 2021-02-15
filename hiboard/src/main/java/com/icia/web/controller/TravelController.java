package com.icia.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icia.common.util.StringUtil;
import com.icia.web.model.HiBoard;
import com.icia.web.model.Paging;
import com.icia.web.util.HttpUtil;

@Controller("TravelController")
public class TravelController {

   private static Logger logger = LoggerFactory.getLogger(HiBoardController.class);
   
   @Value("#{env['auth.cookie.name']}")
   private String AUTH_COOKIE_NAME;
   
   @RequestMapping(value="/")
   public String mainHome(ModelMap model, HttpServletRequest request, HttpServletResponse response)
   {
            
      return "/";
   }
}