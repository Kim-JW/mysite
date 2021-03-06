<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url = "/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
		<form action="${pageContext.request.contextPath }/board?a=write" method="post">
			<c:choose>
				<c:when test = "${not empty groupNo }">
					<input type = "hidden" name = "no" value="${no}">
					<input type = "hidden" name = "g_no" value="${groupNo }">
					<input type = "hidden" name = "o_no" value="${orderNo }">
				</c:when>
			</c:choose>
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글쓰기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td><input type="text" name="title"></td>
					</tr>
					<tr>
						<td class="label">내용 </td>
						<td>
							<div class="view-content">
								<textarea name="contents" cols=50 rows=5></textarea></td>
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<td colspan=4 align=right><input type="submit" value="등록"></td>
				</form>	
				</div>
			</div>
		</div>
		<c:import url = "/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url = "/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>