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
		Connection con = null;

		ResultSet result = null;
		PrintWriter out = response.getWriter();
		
		try {
			String name = request.getParameter("username");
			DBConnection database=new DBConnection();
			boolean b=database.checkDatabase(name);
			if(b==true)
			{
		        RequestDispatcher rd=request.getRequestDispatcher("registration.jsp"); 
		        out.print("allready  registered");
		        rd.include(request, response);
		        return;

			}
			
			

			con = DBConnection.getConnection();

			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String number = request.getParameter("mobilenumber");
			String name1 = request.getParameter("username");
			String password = request.getParameter("password");

			String sql = "insert into login values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, number);
			ps.setString(4, name1);
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
