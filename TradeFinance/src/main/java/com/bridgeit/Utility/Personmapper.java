package com.bridgeit.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bridgeit.model.Usermodel;

public class Personmapper implements RowMapper<Usermodel>{

	@Override
	public Usermodel mapRow(ResultSet rs, int rowNum) throws SQLException {
		Usermodel user = new Usermodel();
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));


		return user;
	}

}
