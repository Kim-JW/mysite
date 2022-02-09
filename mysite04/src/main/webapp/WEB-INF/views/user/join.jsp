<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form
				modelAttribute = "userVo" 
				id="join-form" 
				name="joinForm" 
				method="post" 
				action="${pageContext.request.contextPath }/user/join">
				
					<label class="block-label" for="name">이름</label>
					<form:input path="name"/>
					<p style ="text-align:left; padding-left:0; color: #f00">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<spring:message code = "${errors.getFieldError('name').codes[0] }"/>
							</c:if>
						</spring:hasBindErrors>
					</p>
					
					<!--
					<input id="name" name="name" type="text" value="${userVo.name }">
					
					<p style ="text-align:left; padding-left:0; color: #f00">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<spring:message code = "${errors.getFieldError('name').codes[0] }"/>
							</c:if>
						</spring:hasBindErrors>
					</p>
					-->
					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<input type="button" value="id 중복체크">
					<p style="text-align:left; padding-left:0; color:#f00">
						<form:errors path="email" />
					</p>	
					<!--  
					<input id="email" name="email" type="text" value="">
					-->
					<!-- 
					<spring:message code = "user.join.label.password"/>
					
					<p style ="text-align:left; padding-left:0; color: #f00">
					<form:input path="email"/>
					<input type="button" value="id 중복체크">
					<form:errors path="email"/>
					</p>
					
					 -->
					
					<label class="block-label"><spring:message code="user.join.label.password" /></label>
					<form:password path="password" />
					<p style="text-align:left; padding-left:0; color:#f00">
						<form:errors path="password" />
					</p>
					
					<!--  
					<input name="password" type="password" value="">
					-->
					
					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여"/>
						<form:radiobutton path="gender" value="male" label="남"/>
					</fieldset>
					
					<!--  
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					-->
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>