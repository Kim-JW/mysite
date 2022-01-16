<%@page import="com.poscoict.mysite.dao.GuestBookDao"%>
<%@page import="com.poscoict.mysite.vo.GuestBookVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<GuestBookVo> list = (List<GuestBookVo>)request.getAttribute("list");
	int i = 1;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
</head>
<body>
	<form action="<%= request.getContextPath()%>/gl?a=add" method="post">
	<table border=1 width=500>
		<tr>
			<td>이름</td><td><input type="text" name="name"></td>
			<td>비밀번호</td><td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td colspan=4><textarea name="message" cols=60 rows=5></textarea></td>
		</tr>
		<tr>
			<td colspan=4 align=right><input type="submit" value="등록"></td>
		</tr>
	</table>
	</form>
	<br>
	
	<%
		
		for(GuestBookVo vo : list) {
	%>
	
	<table width=510 border=1>
		<tr>
			<td>[<%=i++%>]</td>
			<td><%=vo.getName()%></td>
			<td><%=vo.getReg_date() %></td>
			<td><a href="<%= request.getContextPath()%>/gl?a=deleteform&no=<%= vo.getNo()%>">삭제</a></td>
		</tr>
		<tr>
			<td colspan=4><%=vo.getMessage().replaceAll("\n", "<br>") %></td>
		</tr>
	</table>
	<%
		}
	%>
</body>
</html>