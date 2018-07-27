package com.bridgeit.DAO;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bridgeit.model.UserModel;

public class UserDAO {

	private DataSource datasource;
	

	public void inserData(UserModel userModel) {

		String query = "insert into Employee (name, email,mobilenumber,city role) values (?,?,?.?.?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = new Object[] { userModel.getName(), userModel.getEmail(), userModel.getMobilenumber(),
				userModel.getCity(), userModel.getRole() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Employee saved with ");
		} else
			System.out.println("Employee save failed");
	}

}
