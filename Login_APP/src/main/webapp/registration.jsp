<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.error-msg {
	color: red;
}
</style>
<script>
	var inValidEmail = true;
	function isValid() {
		var i, count = 0;
		var emailId = document.get
		var input = document.getElementsByTagName("input");
		for (i = 0; i < input.length; i++) {
			if (!input[i].value) {
				count++;
			}
		}
		if (count > 0 || inValidEmail) {
			return false;
		}
		return true;
	}
	function onCheckInput(element, label) {
		if (element.value && label !== 'email') {
			element.nextElementSibling.innerHTML = "";
		} else {
			switch (label) {
			case 'firstname':
				element.nextElementSibling.innerHTML = "first name is required";
				break;
			case 'lastname':
				element.nextElementSibling.innerHTML = "last name is required";
				break;
			case 'mobilenumber':
				element.nextElementSibling.innerHTML = "mobile number is required";
				break;
			case 'email':
				var emailId = element.value;
				var pattern = /^\w[^_]+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w[a-zA-Z]{2,3})+$/;
				inValidEmail = !pattern.test(emailId);
				element.nextElementSibling.innerHTML = inValidEmail ? "Enter valid email Id"
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
	<form method="post" action="RegisterController"
		name="registration_form" id="registration_form">
		<center>
			<table bgcolor="azure" border="1" width="30%" cellpadding="5">
				<thead>
					<tr>
						<th colspan="2">Register Here</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>First Name</td>
						<td><input type="text" id="firstName" 
							maxlength="20" class="first-name" placeholder="First name"
							oninput="onCheckInput(this, 'firstname')" name="firstname"
							required /> <span class="error-msg" id="firstNameError">first
								name is required</span></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><input type="text" maxlength="20" class="last-name"
							placeholder="Last Name" oninput="onCheckInput(this, 'lastname')"
							name="lastname" required /> <span class="error-msg"
							id="lastNameError">last name is required</span></td>
					</tr>
					<tr>
						<td>Mob No.</td>
						<td><input type="number"
							onKeyDown="if(this.value.length==10 && event.keyCode!=8) return false;"
							oninput="onCheckInput(this, 'mobilenumber')" class="mobile-num"
							placeholder="Mobile Number" name="mobilenumber" required /> <span
							class="error-msg" id="mobileNumberError">mobile number is
								required</span></td>
					</tr>
					<tr>
						<td>Email Id</td>
						<td><input type="email" class="email" placeholder="Email Id"
							id="emailId" oninput="onCheckInput(this, 'email')"
							name="username" required /> <span class="error-msg"
							id="emailError">email is required</span></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" class="password"
							placeholder="Password" maxlength="20" name="password"
							oninput="onCheckInput(this, 'password')" required /> <span
							class="error-msg" id="passwordError">password is required</span></td>
					</tr>
					<tr>
						<td><input type="submit" value="Submit"
							onclick="return isValid()" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<%
								String msg1 = (String) (request.getAttribute("message"));
								if (msg1 != null) {
									out.print(request.getAttribute("message"));
								}
							%> <a href="index.jsp">Login Here</a>
						</td>
					</tr>
				</tbody>
			</table>
		</center>
	</form>
</body>
</html>