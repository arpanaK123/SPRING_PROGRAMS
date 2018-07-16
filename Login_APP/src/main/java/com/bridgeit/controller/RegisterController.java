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

import com.bridgeit.utility.DBConnection;

public class RegisterController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		Connection conection = null;

		ResultSet result = null;

		try {
			DBConnection database = new DBConnection();
			String name = request.getParameter("username");
			boolean b = database.checkDatabase(name);
			if (b == true) {
				RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
				request.setAttribute("message", "Allready exist");
				rd.include(request, response);
				return;
			}

			conection = DBConnection.getConnection();

			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String number = request.getParameter("mobilenumber");
			String mail = request.getParameter("username");
			String password = request.getParameter("password");

			String sql = "insert into login values(?,?,?,?,?)";
			PreparedStatement preparestatement = conection.prepareStatement(sql);

			preparestatement.setString(1, firstname);
			preparestatement.setString(2, lastname);
			preparestatement.setString(3, number);
			preparestatement.setString(4, mail);
			preparestatement.setString(5, password);

			int i = preparestatement.executeUpdate();
			if (i > 0) {
				RequestDispatcher requestdispatcher = request.getRequestDispatcher("registration.jsp");
				request.setAttribute("message", "Registration successfull");
				requestdispatcher.include(request, response);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
