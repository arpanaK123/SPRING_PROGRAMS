package com.bridgeit.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import com.bridgeit.model.UserPoso;

public class RegistrationFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("filter");
		PrintWriter printWriter = response.getWriter();
		RequestDispatcher requestDispatcher = null;
		System.out.println("request");
		if (request.getParameter("firstname") != null && request.getParameter("lastname") != null
				&& request.getParameter("mobilenumber") != null && request.getParameter("username") != null
				&& request.getParameter("password") != null) {
			UserPoso userPoso = new UserPoso();
			String firstName = request.getParameter("firstname");
			System.out.println("firstName");
			userPoso.setFirst_Name(firstName);
			String lastName = request.getParameter("lastname");
			userPoso.setLast_Name(lastName);
			String number = request.getParameter("mobilenumber");
			userPoso.setMobile_Number(number);
			String mail = request.getParameter("username");
			System.out.println(mail);
			RegistrationFilter.validateEmail(mail);
			userPoso.setEmail(mail);
			String passWord = request.getParameter("password");
			userPoso.setPassWord(passWord);
			System.out.println(passWord);
			request.setAttribute("userPoso", userPoso);
			chain.doFilter(request, response);

		} else {
			printWriter.print("<p id='errMsg' style='color: red; font-size: larger;'>Something wrong ....!</p>");
			requestDispatcher = request.getRequestDispatcher("registration.jsp");
			requestDispatcher.include(request, response);

		}
	}

	String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	static Pattern pattern;
	static Matcher matcher;

	public RegistrationFilter() {
		// initialize the Pattern object
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		System.out.println(pattern);
	}

	public static boolean validateEmail(String mail) {
		System.out.println("mail= " + mail);
		matcher = pattern.matcher(mail);
		return matcher.matches();
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

}
