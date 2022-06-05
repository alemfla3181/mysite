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
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
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

					<c:set var='count' value='${fn:length(list) }' />
					<c:forEach items='${list }' var='vo' varStatus='status'>
						<tr>
							<td>${page.count-(5)*(page.page-1)-status.index}</td>
							<td style="text-align: left; padding-left: ${(vo.depth-1)*10}px">				
							<c:if test="${vo.depth > 1 }">
								<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" />
							</c:if>					 
							<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${vo.no}">${vo.title }			
							</a></td>	
							<td>${vo.user_name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<td>
							<c:if test="${authUser.no == vo.user_no }">
								<a href="${pageContext.servletContext.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a>
							</c:if>						
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul><c:if test="${page.page != 1}">
							<c:choose>
								<c:when test="${param.kwd!=null}">
									<li><a style="color:orange" href="${pageContext.servletContext.contextPath }/board?pg=${page.page-1}&kwd=${param.kwd}">◀</a></li>
								</c:when>
								<c:otherwise>
									<li><a style="color:orange" href="${pageContext.servletContext.contextPath }/board?pg=${page.page-1}">◀</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>												
						<c:forEach var='pg' begin="${page.startPage }" end="${page.totalSize}">
						<c:choose>
							<c:when test="${pg == page.page}">
								<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?pg=${page.page}">${pg}</a></li>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${page.lastPage < pg}">
										<li >${pg}</li>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${param.kwd!=null}">
												<li ><a href="${pageContext.servletContext.contextPath }/board?pg=${pg}&kwd=${param.kwd}">${pg}</a></li>
											</c:when>
											<c:otherwise>
												<li ><a href="${pageContext.servletContext.contextPath }/board?pg=${pg}">${pg}</a></li>
											</c:otherwise>
										</c:choose>										
									</c:otherwise>
								</c:choose>							
							</c:otherwise>
						</c:choose>
						</c:forEach>
						<c:if test="${page.page != page.lastPage}">
							<c:choose>
								<c:when test="${param.kwd!=null}">
									<li><a style="color:orange" href="${pageContext.servletContext.contextPath }/board?pg=${page.page+1}&kwd=${param.kwd}">▶</a></li>
								</c:when>
								<c:otherwise>
									<li><a style="color:orange" href="${pageContext.servletContext.contextPath }/board?pg=${page.page+1}">▶</a></li>
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
							<a href="${pageContext.request.contextPath}/board?a=writeform"
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