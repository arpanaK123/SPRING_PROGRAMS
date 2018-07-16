package com.bridgeit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bridgeit.utility.DBConnection;

public class LoginController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {

			Connection connection = null;
			connection = DBConnection.getConnection();

			PreparedStatement preparestatement = connection
					.prepareStatement("select User_Name,PassWord from login where User_Name=? and PassWord=?");
			preparestatement.setString(1, username);
			preparestatement.setString(2, password);

			ResultSet resultset = preparestatement.executeQuery();
			while (resultset.next()) {

				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				Cookie cookie = new Cookie("username", username);
				cookie.setMaxAge(30*60);
				response.addCookie(cookie);
				
				
				String url=response.encodeURL("welcome.jsp");

				response.sendRedirect(url);
				return;
			}
			RequestDispatcher requestdispatcher = request.getRequestDispatcher("index.jsp");
			request.setAttribute("message", "Incorrect email or password ");
			requestdispatcher.include(request, response);
			return;

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
