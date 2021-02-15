<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String title = request.getParameter("title");     %>    
<%
String fromDate = request.getParameter("fromDate");
String toDate = request.getParameter("toDate");
int fromDate1 = Integer.parseInt(fromDate);
int toDate1 = Integer.parseInt(toDate);

int i;
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border=1>
	<tr>
		<th>날짜</th>
		<th>일정</th>
	</tr>
	<tr>
		<td>hh</td>
		<td>hh</td>
	</tr>
	<tr>
		<td>
		<div>
		<%
		
		   for(i=1;i<=(toDate1-fromDate1)+1;i++)
		   {
		%>
		   <table id="demo<%=i%>">
		      <tr>
		         <th><button id="day<%=i%>" class="row<%=i%>">Day<%=i %></button></th>
		         <th><input type='button' class="row<%=i%>" value='일정 삭제' onclick='deleteRow<%=i %>(0)' /></th>
		      </tr>
		   </table>
		<%
		   }      
		%>
		</div>
	</tr>
</table>

</body>
</html>