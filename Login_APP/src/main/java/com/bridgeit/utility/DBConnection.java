package com.bridgeit.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
