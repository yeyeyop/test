<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	javax.servlet.RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index");//  /index로 디스패쳐시킴 얘를 가지고 컨트롤러로 감
	requestDispatcher.forward(request, response);
%>
