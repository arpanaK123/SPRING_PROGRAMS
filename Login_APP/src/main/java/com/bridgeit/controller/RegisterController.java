package com.bridgeit.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgeit.utility.DBConnection;

public class RegisterController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection con = null;

		try {
			con = DBConnection.getConnection();
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String number = request.getParameter("mobilenumber");
			String name = request.getParameter("username");
			String password = request.getParameter("password");
			String sql = "insert into login values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, number);
			ps.setString(4, name);
			ps.setString(5, password);

			int i = ps.executeUpdate();
			if (i > 0) {
				response.sendRedirect("index.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
