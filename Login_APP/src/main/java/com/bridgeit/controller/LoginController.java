package com.bridgeit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bridgeit.utility.DBConnection;

public class LoginController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		if (un == "" || pw == "") {
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("message", "Incorrect email or password / not register");
			rd.include(request, response);
			return;
		}

		try {

			Connection con = null;
			con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("select User_Name,PassWord from login where User_Name=? and PassWord=?");
			ps.setString(1, un);
			ps.setString(2, pw);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				HttpSession session = request.getSession();
				session.setAttribute("username", un);
				response.sendRedirect("welcome.jsp");
				return;
			}
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("message", "Incorrect email or password / not register");
			rd.include(request, response);
			return;
			//response.sendRedirect("error.jsp");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
