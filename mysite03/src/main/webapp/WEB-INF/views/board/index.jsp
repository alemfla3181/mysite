<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="get">
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

					<c:forEach items='${map.list }' var='vo' varStatus='status'>
						<tr>
							<td>${map.count-(5)*(map.prevPage-1)-status.index}</td>
							<td style="text-align: left; padding-left: ${(vo.depth-1)*10}px">				
							<c:if test="${vo.depth > 1 }">
								<img src="${pageContext.request.contextPath }/assets/images/reply.png" />
							</c:if>					 
							<c:choose>
								<c:when test='${map.query != ""}'>
									<a href="${pageContext.request.contextPath }/board/view/${vo.no}?pg=${map.prevPage}&kwd=${map.query}">${vo.title }</a>	
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath }/board/view/${vo.no}?pg=${map.prevPage}">${vo.title }</a>	
								</c:otherwise>
							</c:choose>		
							</td>	
							<td>${vo.user_name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<td>
							<c:if test="${authUser.no == vo.user_no }">
								<c:choose>
									<c:when test='${map.query != ""}'>
										<a href="${pageContext.request.contextPath }/board/delete/${vo.no}?pg=${map.prevPage}&kwd=${map.query}" class="del">삭제</a>	
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath }/board/delete/${vo.no}?pg=${map.prevPage}" class="del">삭제</a>
									</c:otherwise>
								</c:choose>	
								
							</c:if>						
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul><c:if test="${map.prevPage > 1}">
							<c:choose>
								<c:when test='${map.query != ""}'>
									<li><a style="color:orange" href="${pageContext.request.contextPath }/board?pg=${map.prevPage-1}&kwd=${map.query}">◀</a></li>
								</c:when>
								<c:otherwise>
									<li><a style="color:orange" href="${pageContext.request.contextPath }/board?pg=${map.prevPage-1}">◀</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>												
						<c:forEach var='page' begin="${map.beginPage }" end="${map.endPage}">
						<c:choose>
							<c:when test="${page == map.prevPage}">
								<li class="selected"><a href="${pageContext.request.contextPath }/board?pg=${map.prevPage}">${page}</a></li>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${map.TotalPage < page}">
										<li >${page}</li>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test='${map.query != ""}'>
												<li ><a href="${pageContext.request.contextPath }/board?pg=${page}&kwd=${map.query}">${page}</a></li>
											</c:when>
											<c:otherwise>
												<li ><a href="${pageContext.request.contextPath }/board?pg=${page}">${page}</a></li>
											</c:otherwise>
										</c:choose>										
									</c:otherwise>
								</c:choose>							
							</c:otherwise>
						</c:choose>
						</c:forEach>
						<c:if test="${map.prevPage < map.TotalPage}">
							<c:choose>
								<c:when test='${map.query != ""}'>
									<li><a style="color:orange" href="${pageContext.request.contextPath }/board?pg=${map.prevPage+1}&kwd=${map.query}">▶</a></li>
								</c:when>
								<c:otherwise>
									<li><a style="color:orange" href="${pageContext.request.contextPath }/board?pg=${map.prevPage+1}">▶</a></li>
								</c:otherwise>
							</c:choose>							
						</c:if>
						</ul>
				</div>
				<!-- pager 추가 -->		

				<div class="bottom">
					<c:choose>
						<c:when test="${empty authUser }">
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/board/write"
								id="new-book">글쓰기</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>