<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<%
		String username = (String) session.getAttribute("username");
		if (username != null) {
			response.sendRedirect("welcome.jsp");
		}
		
	%>
	<form method="post" action="LoginController">
		<center>
			<table bgcolor="azure" border="1" width="30%" cellpadding="3">
				<thead>
					<tr>
						<th colspan="2">Login Here</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Email</td>
						<td><input type="text" name="username" value="" /></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" name="password" value="" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Login" /></td>
						
						<td colspan="2"><% String msg = (String)(request.getAttribute("message"));
								
							if(msg!=null){
								out.print(request.getAttribute("message"));
							}
							%>
						
					</tr>
					<tr>
						<td colspan="2">Yet Not Registered!! <a
							href="registration.jsp">Register Here</a></td>
					</tr>
				</tbody>
			</table>
		</center>
	</form>
</body>
</html>