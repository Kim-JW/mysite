<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board?a=search" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<c:set var="count" value="${fn: length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count-status.index }</td>
							<c:choose>
								<c:when test="${1 eq vo.depth }">
									<td style="text-align: left; padding-left: 0px"><a
										href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a></td>
								</c:when>

								<c:otherwise>
			
									<td style="text-align:left; padding-left:${20*vo.depth}px"><img
							src="${pageContext.servletContext.contextPath }/assets/images/reply.png" /><a
										href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a></td>
								</c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${authUser.no eq vo.userNo }">
									<td><a
										href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}"
										class="del"
										style='background-image: url("${pageContext.request.contextPath }/assets/images/recycle.png")'>삭제</a></td>
								</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				
				<div class = "bottom">
				<p style="text-align:center;">
				<c:choose>
					<c:when test="${1 ne currentPage }">
				
				<a href="${pageContext.request.contextPath }/board?currentPage=${currentPage-1}">◀</a>
					</c:when>
				</c:choose> 
				
				<c:set var="pageNum" value="${pageNum }" />
					<c:forEach var = "i" begin="1" end = "${pageNum }">
						<c:choose>
						<c:when test="${i eq currentPage }">
						<a href="${pageContext.request.contextPath }/board?currentPage=${i}"><font size="3" color = "red">${i }</font></a>
						</c:when>
						<c:otherwise>
						<a href="${pageContext.request.contextPath }/board?currentPage=${i}"><font size="3">${i }</font></a>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				
				<c:choose>
					<c:when test="${pageNum ne currentPage }">
				
				<a href="${pageContext.request.contextPath }/board?currentPage=${currentPage+1}">▶</a>
					</c:when>
				</c:choose>
					
					</p>
				</div>
				
				<c:choose>
					<c:when test="${not empty authUser }">
						<div class="bottom">
							<a
								href="${pageContext.servletContext.contextPath }/board?a=writeform"
								id="new-book">글쓰기</a>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>