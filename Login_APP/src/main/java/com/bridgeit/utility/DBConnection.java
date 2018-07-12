package com.bridgeit.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bridgeit.model.UserPoso;

public class DBConnection {
	private static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/LoginApp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "arpana";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection connection = null;

		Class.forName(DB_DRIVER_CLASS);

		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

		return connection;
	}

	public static boolean checkDatabase(String name) throws SQLException, ClassNotFoundException {
		Connection con = null;
		con = DBConnection.getConnection();

		List<UserPoso> list = new ArrayList();

		PreparedStatement stmt = con.prepareStatement("select * from login");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			UserPoso userposo = new UserPoso();
			userposo.setEmail(rs.getString("User_Name"));
			list.add(userposo);

		}
		for (UserPoso username : list) {
			if (username.getEmail().equals(name)) {
				return true;
			}
		}
		return false;

	}
}
