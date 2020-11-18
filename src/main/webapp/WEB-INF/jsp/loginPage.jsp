<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Login</title>

	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>

	<script type ="text/javascript">
        function login(){

            var username = $("#loginUsername").val();
            var password = $("#loginPassword").val();


			$.ajax({
				url:"/loginProcess",
				type:"post",
				data:"username="+username+"&password="+password,
				dataType:"json",
				success:function(data){
					if(data == true){
						location.href="/board/list/1";
					}
					else{
						alert(' 로그인 오류 : 입력하신 이름이나 비밀번호가 맞지 않습니다.');
						return false;
					}
				},
				error:function(error){

					alert(' 로그인 오류 : 에러');
					return false;
				}
			});

        }


	</script>
</head>
<body>
<input id="loginUsername" placeholder="이름" style="width: 240px;">
<br>
<input type="password" id="loginPassword" placeholder="비밀번호" style="width: 240px;">
<br>
<button type="submit" onclick="login();" style="width: 240px;" >로그인</button>

</body>
</html>