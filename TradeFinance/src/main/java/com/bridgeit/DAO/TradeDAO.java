package com.bridgeit.DAO;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.stereotype.Repository;

import com.bridgeit.Utility.BcryptHash;
import com.bridgeit.model.TradeUser;
import com.bridgeit.model.UserModel;

@Repository
public class TradeDAO {
	@Autowired
	private DataSource datasource;

	public int inserData(UserModel userModel) {

		String query = "insert into login (name, email,city,role,password,verified,Authentication_key,bankname,useraccount,TradeUser,accountnumber,balance) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = new Object[] { userModel.getName(), userModel.getEmail(), userModel.getCity(),
				userModel.getRole(), userModel.setPassword(BcryptHash.generatedHashPassword(userModel.getPassword())),
				userModel.isVerified(), userModel.getAuthentication_key(), userModel.getBankname(),
				userModel.getUseraccount(), new SqlLobValue(userModel.getTradeUser()), userModel.getAccountnumber(),
				userModel.getBalance() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Employee saved ");
		} else {
			System.out.println("Employee save failed");
		}
		return out;
	}

	public boolean presenceUser(UserModel user) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { user.getEmail() };
		String query = "select name from login where email = ?";

		try {
			String name = jdbcTemplate.queryForObject(query, args, String.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
