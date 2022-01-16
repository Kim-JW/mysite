<%@page import="com.poscoict.mysite.dao.GuestBookDao"%>
<%@page import="com.poscoict.mysite.vo.GuestBookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <%
 	request.setCharacterEncoding("utf-8");
 
 	String name = request.getParameter("name");
	String password = request.getParameter("password");
 	String message = request.getParameter("message");
 	
 	GuestBookVo vo = new GuestBookVo();
 	vo.setName(name);
	vo.setPassword(password);
	vo.setMessage(message);
	
	boolean result = new GuestBookDao().insert(vo);
	
	response.sendRedirect("/guestbook01/index.jsp");
 %>
 