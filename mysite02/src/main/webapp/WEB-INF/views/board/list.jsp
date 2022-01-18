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
				<form id="search_form" action="" method="post">
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
							<td  style="text-align:left"><a href="${pageContext.request.contextPath }/board?a=viewform">${vo.title }</a></td>
							<td>${vo.getUserName() }</td>
							<td>${vo.hit }</td>
							<td>${vo.getRegDate() }</td>
							<c:choose>
								<c:when test="${authUser.name==vo.getUserName() }">
									<td><a href="${pageContext.servletContext.contextPath }/board?a=delete&no=${vo.no}" class="del" style="background-image:url('${pageContext.servletContext.contextPath }/assets/images/recycle.png')">삭제</a></td>
								</c:when>
								<c:otherwise>
									
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
					<tr>
						<td>2</td>
						<!-- style="padding-left:${(vo.depth-1)*20}px" -->
						<td style="text-align:left, padding-left:20px">
							<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png"/>
							<a href="">두 번째 글입니다.</a>
							
						</td>
						<td>안대혁</td>
						<td>3</td>
						<td>2015-10-02 12:04:12</td>
						<td><a href="${pageContext.servletContext.contextPath }/board?a=delete" class="del" style="background-image:url('${pageContext.servletContext.contextPath }/assets/images/recycle.png')">삭제</a></td>
					</tr>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>					
				<!-- pager 추가 -->
				<c:choose>
					<c:when test="${not empty authUser }">
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
						</div>				
					</c:when>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>