package com.bridgeit.DAO;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bridgeit.Utility.BcryptHash;
import com.bridgeit.model.UserModel;

@Repository
public class UserDAO {
	@Autowired
	private DataSource datasource;

	public int inserData(UserModel userModel) {

		String query = "insert into login (id,name, email,city,role,password,verified) values (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = new Object[] { userModel.getId(), userModel.getName(), userModel.getEmail(),
				userModel.getCity(), userModel.getRole(), userModel.setPassword(BcryptHash.generatedHashPassword(userModel.getPassword())), userModel.isVerified() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Employee saved ");
		} else {
			System.out.println("Employee save failed");
		}
		return out;
	}

	public boolean existenceUser(UserModel user) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		System.out.println(user);
		Object[] args = { user.getEmail() };
		String query = "select name from login where email = ?";

		try {
			String name = (String) jdbcTemplate.queryForObject(query, new Object[] { user.getEmail() }, String.class);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

	}

	public boolean presenceUser(UserModel user) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		System.out.println(user);
		Object[] args = { user.getEmail() };
		String query = "select name from login where email = ?";

		try {
			String name = jdbcTemplate.queryForObject(query, new Object[] { user.getEmail() }, String.class);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(user);
			return false;
		}

	}

	public boolean checkUser(String userEmail, String userPassword) {
		System.out.println(userEmail);
		Object[] args = { userEmail, userPassword };
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		UserModel user = null;
		try {
			String userName = jdbcTemplate.queryForObject("select name from login where email=? and password=?",
					String.class, args);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
