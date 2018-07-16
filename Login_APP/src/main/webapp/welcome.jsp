<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome page</title>
</head>
<body>
	<%
	
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
	for(Cookie cookie : cookies){
		if(cookie.getName().equals("username")) userName = cookie.getValue();
		if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
		else
		{
			sessionID=session.getId();
		}
	}
	}
	out.print("name: "+userName);
	if(userName==null)
	{
		response.sendRedirect("index.jsp");
	}
	%>
	
	<h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %></h3>
	*....welcome....*
	<br> login successful
	<form method="post" action="LogoutController">
		<br> <input type="submit" value="Logout" />
	</form>
</body>
</html>