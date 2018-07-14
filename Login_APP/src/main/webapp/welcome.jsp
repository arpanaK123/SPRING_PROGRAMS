<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		
		String user = null;
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
		} else
			user = (String) session.getAttribute("user");
	%>
	*....welcome....*
	<br> login successful
	<form method="post" action="LogoutController">
		<br> <input type="submit" value="Logout" />
	</form>
</body>
</html>