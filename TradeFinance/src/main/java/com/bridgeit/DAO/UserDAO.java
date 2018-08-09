package com.bridgeit.DAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.bridgeit.Utility.BcryptHash;
import com.bridgeit.Utility.PersonMapper;
import com.bridgeit.model.UserModel;

@Repository
public class UserDAO {
	@Autowired
	private DataSource datasource;

	public int inserData(UserModel userModel) {

		String query = "insert into login (name, email,city,role,password,verified,Authentication_key) values (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = new Object[] { userModel.getName(), userModel.getEmail(), userModel.getCity(),
				userModel.getRole(), userModel.setPassword(BcryptHash.generatedHashPassword(userModel.getPassword())),
				userModel.isVerified(), userModel.getAuthentication_key() };

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
			String name = (String) jdbcTemplate.queryForObject(query, args, String.class);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

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

	public boolean checkUser(String userEmail, String userPassword) {
		System.out.println(userEmail);
		Object[] args = { userEmail };
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		UserModel user = null;
		try {

			String password = jdbcTemplate.queryForObject("select password from login where email=?", String.class,
					args);
			System.out.println(password);

			System.out.println(userPassword);
			System.out.println(BCrypt.checkpw(userPassword, password));
			return BCrypt.checkpw(userPassword, password);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public UserModel getPersonByEmail(String email) {
		System.out.println();
		try {
			String fIND_PERSON = "select * from login where email = ?";
			Object[] args = { email };
			JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
			return jdbcTemplate.queryForObject(fIND_PERSON, args, new PersonMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public UserModel getUserByUniqueKey(String authentication_key) {
		System.out.println("key: " + authentication_key);

		Object[] args = { authentication_key };
		String fIND_PERSON = "select * from login where authentication_key = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		return jdbcTemplate.queryForObject(fIND_PERSON, args, new PersonMapper());
	}

	public boolean update(UserModel person) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String update_PERSON = "update login set verified = ? where id = ?";
		return jdbcTemplate.update(update_PERSON, person.isVerified(), person.getId()) > 0;
	}

	public boolean checkUserForResetpassword(String userEmail) {
		System.out.println(userEmail);
		Object[] args = { userEmail };
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String email = jdbcTemplate.queryForObject("select email from login where email=?", String.class, args);
		if (email != null) {

			return true;
		} else {
			return false;
		}
	}

	public boolean getVerified(String authentication_key) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		boolean verified = true;
		Object[] args = { verified, authentication_key };

		String sql = "update login set verified = ? where authentication_key = ?";
		try {
			int result = jdbcTemplate.update(sql, args);

			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public UserModel fetchUserByEmail(String email) {

		Object[] args = { email };
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "select * from login where email=?";
		List<UserModel> user = jdbcTemplate.query(sql, args, new PersonMapper());
		UserModel user1 = user.get(0);
		return user1;
	}

	public boolean userResetPassword(String authentication_key, String newPassword) {
		Object[] args = {newPassword, authentication_key };

		String sql = "update login set password = ? where authentication_key=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		try {
			int status = jdbcTemplate.update(sql, args);
			if (status == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	

}
