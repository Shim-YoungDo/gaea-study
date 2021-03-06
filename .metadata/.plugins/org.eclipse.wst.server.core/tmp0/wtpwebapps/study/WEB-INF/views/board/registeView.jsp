<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/board/registerView.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>

<h1>게시판 등록</h1>
<form id="enroll_form" action="/board/register" method="post">
<c:if test="${member != null}">
	<div class="input_wrap">
		<label>Title</label>
		<input class="boardTitle" name="boardTitle">
		<span class="title_check">제목을 입력해주세요.</span>
	</div>
	<div class="input_wrap">
		<label>Content</label>
		<textarea rows="3" class="boardContent" name="boardContent"></textarea>
		<span class="content_check">내용을 입력해주세요.</span>
	</div>
	<div class="input_wrap">
		<label>Writer</label>
		<input class="writer_input" name="boardWriter" readonly="readonly" value="${sessionScope.member.memberID}">
	</div>
	<button class="enroll_button">등록</button>
	<!-- <button class="cancel_button">취소</button>-->
	</c:if>
</form>
<c:if test="${member == null}">
	<p>로그인 후에 작성해주세요.</p>
</c:if>

<script>

var titleCheck = false;
var contentCheck = false;

$(document).ready(function(){
	$(".enroll_button").click(function(){
		var title = $('.boardTitle').val();
		var content = $('.boardContent').val();
		
		if(title == ""){
			//$('.final_id_ck').css('display','block');
			titleCheck = false;
		}else{
			//$('.final_id_ck').css('display', 'none');
			titleCheck = true;
		}
		
		if(content == ""){
			//$('.final_id_ck').css('display','block');
			contentCheck = false;
		}else{
			//$('.final_id_ck').css('display', 'none');
			contentCheck = true;
		}
		
		if(titleCheck&&contentCheck){
			$("#enroll_form").submit();		
			
		}
		else{
			alert("잘못된 형식입니다.");
		}
		
		return false;
	});
});
</script>

</body>
</html>