<%@page import="com.poscoict.mysite.vo.GuestBookVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<GuestBookVo> list = (List<GuestBookVo>)request.getAttribute("list");
	int i = 1;
%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>MySite</h1>
			<ul>
				<li><a href="">로그인</a><li>
				<li><a href="">회원가입</a><li>
				<li><a href="">회원정보수정</a><li>
				<li><a href="">로그아웃</a><li>
				<li>님 안녕하세요 ^^;</li>
			</ul>
		</div>
		<div id="content">
			<div id="guestbook">
				<form action="<%= request.getContextPath()%>/guestbook?a=add" method="post">
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
			<td><a href="<%= request.getContextPath()%>/guestbook?a=deleteform&no=<%= vo.getNo()%>">삭제</a></td>
		</tr>
		<tr>
			<td colspan=4><%=vo.getMessage().replaceAll("\n", "<br>") %></td>
		</tr>
	</table>
	<%
		}
	%>
			</div>
		</div>
		<div id="navigation">
			<ul>
				<li><a href="<%= request.getContextPath()%>/">재웅재웅</a></li>
				<li><a href="">방명록</a></li>
				<li><a href="">게시판</a></li>
			</ul>
		</div>
		<div id="footer">
			<p>(c)opyright 2015, 2016, 2017, 2018</p>
		</div>
	</div>
</body>
</html>