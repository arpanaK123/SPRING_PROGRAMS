<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.error-msg {
	color: red;
}
</style>
<script type="text/javascript">
	function loginvalid() {
		var i, count = 0;
		var input = document.getElementsByClassName("input-field");
		for (i = 0; i < input.length; i++) {
			if (!input[i].value) {
				count++;
			}
		}
		if (count > 0) {
			return false;
		}
		return true;
	}
	function onCheckInput(element, label) {
		if (element.value && label !== 'email') {
			element.nextElementSibling.innerHTML = "";
		} else {
			switch (label) {
			case 'email':
				element.nextElementSibling.innerHTML = !element.value ? "email is required"
						: '';
				break;
			case 'password':
				element.nextElementSibling.innerHTML = "password is required";
				break;
			default:
				element.nextElementSibling.innerHTML = "";
			}
		}
	}
</script>
</head>

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
						<td>Email-Id</td>
						<td><input type="email" class="input-field"
							placeholder="Email Id" id="emailId"
							oninput="onCheckInput(this, 'username')" name="username" required />
							<span class="error-msg" id="emailError">email is required</span></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" class="input-field"
							placeholder="Password" name="password"
							oninput="onCheckInput(this, 'password')" required /> <span
							class="error-msg" id="passwordError">password is required</span></td>
					</tr>
					<td><input type="submit" value="Login" /></td>

					<td colspan="2">
						<%
							String msg = (String) (request.getAttribute("message"));
							if (msg != null) {
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