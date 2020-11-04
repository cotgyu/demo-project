<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>MemberList</title>

	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<script type ="text/javascript">

	</script>
</head>
<body>

	<c:forEach var="member" items="${memberList}">

	username : ${member.username}
	</c:forEach>

</body>
</html>