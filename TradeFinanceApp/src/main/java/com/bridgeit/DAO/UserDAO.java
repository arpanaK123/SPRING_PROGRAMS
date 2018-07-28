package com.bridgeit.DAO;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bridgeit.model.UserModel;
@Repository
public class UserDAO {
	@Autowired
	private DataSource datasource;

	public int inserData(UserModel userModel) {

		String query = "insert into login (name, email,mobilenumber,city role) values (?,?,?.?.?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = new Object[] { userModel.getName(), userModel.getEmail(), userModel.getMobilenumber(),
				userModel.getCity(), userModel.getRole() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Employee saved ");
		} else {
			System.out.println("Employee save failed");
		}
		return out;
	}

}
