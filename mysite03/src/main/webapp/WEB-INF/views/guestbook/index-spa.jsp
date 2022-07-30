<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
// Application
var render = function(vo, mode) {
	var htmls = 
		"<li data-no=''>" +
		"<strong>" + vo.name + "</strong>" +
		"<p>" + vo.message.replace(/\n/gi, "<br/>") + "</p>" +
		"<strong></strong>" +
		"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
		"</li>";
		
	$("#list-guestbook")[mode ? "prepend" : "append"](htmls);
}

var fetch = function(){
	$.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook",
		type: "get",
		dataType: "json",
		success: function(response) {
			if(response.result !== 'success'){
				console.error(response.message);
				return;
			}				
			response.data.forEach(function(vo){
				render(vo, true);
			});
		}		
	});
}

$(function(){
	$("#add-form").submit(function(event){
		event.preventDefault();		
		var vo = {};
		
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
	
		$.ajax({
			url: "${pageContext.request.contextPath}/api/guestbook",
			type: "post",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(vo),
			success: function(response) {
				if(response.result !== 'success'){
					console.error(response.message);
					return;
				}
				render(response.data, true);
			}
		});
	});
	
	// 삭제 다이알로그 jQuery객체를 미리 만들기
		var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function() {
				var no = $("#hidden-no").val();
				var password = $("#password-delete").val();
				
				$.ajax({
					url: "${pageContext.request.contextPath}/api/guestbook/" + no,
					type: "delete",
					dataType: "json",
					data: "password=" + password,
					success: function(response) {
						if(response.result != "success"){
							console.error(response.message);
							return;
						}
						if(response.data != -1){
							$("#list-guestbook li[data-no=" + response.data + "]").remove();
							dialogDelete.dialog('close');
							return;
						}					
					}
				});
			},
			"취소": function() {
					$(this).dialog('close');
			}
		},
		close: function(){
			$("#hidden-no").val("");
			$("#password-delete").val("");
			$("#dialog-delete-form p.validateTips.error").hide();
		}
	});
		
	// 글 삭제 버튼 Click 이벤트 처리(Live Event)
	$(document).on('click', '#list-guestbook li a', function(event){
		event.preventDefault();		
		$("#hidden-no").val($(this).data('no'));
		dialogDelete.dialog('open');
	});
	
	fetch();
});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">									
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>