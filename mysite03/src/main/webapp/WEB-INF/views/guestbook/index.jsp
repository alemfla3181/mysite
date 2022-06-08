<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<% pageContext.setAttribute("newLine", "\n"); %>
<% pageContext.setAttribute("newSpace", " "); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request.contextPath}/guestbook/add" method="post">
					<table border=1 width=500>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="message" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<br/>
				<hr/>
								
				<ul>
					<li>
					<c:set var='count' value='${fn:length(list) }' />
					
					<c:forEach items='${list }' var='vo' varStatus='status' >
						<table border=1 width=500>
							<tr>
								<td>${count-status.index}</td>
								<td>${vo.name }</td>
								<td>${vo.dateTime }</td>
								<td>							
								<a href="${pageContext.request.contextPath}/guestbook/delete/${vo.no}">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4>
								${fn:replace(fn:replace(vo.message, newSpace, '&nbsp;'), newLine, '<br />')}
								</td>
							</tr>
						</table> <br>
					</c:forEach>						
					</li>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>	
</body>
</html>