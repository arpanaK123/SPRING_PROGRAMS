package com.bridgeit.Utility;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bridgeit.model.UserModel;

public class PersonMapper implements RowMapper<UserModel> {

	@Override
	public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserModel user = new UserModel();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setCity(rs.getString("city"));
		user.setRole(rs.getString("role"));
		user.setAuthentication_key(rs.getString("authentication_key"));
		user.setVerified(rs.getBoolean("verified"));
		user.setBankname(rs.getString("bankname"));
		user.setUseraccount(rs.getBytes("useraccount"));
		user.setAccountnumber(rs.getString("accountnumber"));
		Blob userBlob = rs.getBlob("useraccount");
		byte [] userByte = null;
		try {
			userByte =userBlob.getBytes(1, (int)userBlob.length());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
//		user.setUserAccount(userByte);
		//user.setUserAccount();
		//return user;
		
		
		user.setBalance(rs.getInt("balance"));
		return user;
	}

}
