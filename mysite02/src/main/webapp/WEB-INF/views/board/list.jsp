<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<c:set var="count" value="${fn:length(list) }"/>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
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
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count-status.index }</td>
							<td  style="text-align:left; padding-left:${(vo.depth-1)*20}px">
								<c:if test="${vo.depth>=2 }">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png"/>
								</c:if>
								<a href="${pageContext.request.contextPath }/board?a=viewform&bno=${vo.no}&uno=${vo.getUserNo()}">${vo.title }</a>
							</td>
							<td>${vo.getUserName() }</td>
							<td>${vo.hit }</td>
							<td>${vo.getRegDate() }</td>
								<c:if test="${authUser.no==vo.getUserNo()&& not empty authUser}">
									<td><a href="${pageContext.servletContext.contextPath }/board?a=delete&no=${vo.no}" class="del" style="background-image:url('${pageContext.servletContext.contextPath }/assets/images/recycle.png')">삭제</a></td>
								</c:if>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
						<ul>
							<li><a href="">◀</a></li>
					<c:forEach begin="0" end="${m['count']}" step="1" var="i">
							<c:choose>
								<c:when test="">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?current=${i}">${i+1}</a></li>
					</c:forEach>
							<li><a href="">▶</a></li>
						</ul>
				</div>					
				<!-- pager 추가 -->
					<c:if test="${not empty authUser }">
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
						</div>				
					</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>