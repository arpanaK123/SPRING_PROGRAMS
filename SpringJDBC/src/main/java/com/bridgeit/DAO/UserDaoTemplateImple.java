package com.bridgeit.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bridgeit.model.UserModel;

public class UserDaoTemplateImple implements UserDAO {

	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public void saveUserData(UserModel user) {

		String query = "insert  into employee(id,name, email, password, mobile_num, address) values(?,?,?,?,?,?) ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] arg = new Object[] { user.getId(), user.getName(), user.getEmail(), user.getPassword(),
				user.getMobile_num(), user.getAddress() };

		int data = jdbcTemplate.update(query, arg);
		if (data != 0) {
			System.out.println("User Data saved by id: " + user.getId());
		} else {
			System.out.println("User Data failed by id: " + user.getId());
		}
	}

	public UserModel userGetById(int id) {
		String query = "select id,name, email, password, mobile_num, address from employee where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		UserModel user = jdbcTemplate.queryForObject(query, new Object[] { id }, new RowMapper<UserModel>() {
			public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setMobile_num(rs.getString("mobile_num"));
				user.setAddress(rs.getString("address"));
				return user;
			}
		});

		return user;
	}

	public void updateUserData(UserModel user) {

		String query = "update employee set  name=?, email=?, password=?, mobile_num=?, address=? where id='1'";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] arg = new Object[] { user.getName(), user.getEmail(), user.getPassword(),
				user.getMobile_num(), user.getAddress()};
		int data = jdbcTemplate.update(query, arg);
		if (data != 0) {
			System.out.println("user data update  " );
		} else {
			System.out.println("user update failed  ");
		}
	}

	public void deleteUserById(int id) {

		String query = "delete from employee where id=?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		int data = jdbcTemplate.update(query, id);
		if (data != 0) {
			System.out.println("user data deleted by id: " + id);
		} else {
			System.out.println("user data not found with id: " + id);
		}

	}

	public List<UserModel> getAll() {
		String query = "select id,name, email, password, mobile_num, address from employee";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		List<UserModel> userList = new ArrayList<UserModel>();
		List<Map<String, Object>> empRows = jdbcTemplate.queryForList(query);
		for (Map<String, Object> empRow : empRows) {
			UserModel usermodel = new UserModel();
			usermodel.setId((Integer) empRow.get("id"));
			usermodel.setName((String.valueOf(empRow.get("name"))));
			usermodel.setEmail((String.valueOf(empRow.get("email"))));
			usermodel.setPassword((String.valueOf(empRow.get("password"))));
			usermodel.setAddress((String.valueOf(empRow.get("address"))));
			usermodel.setMobile_num((String.valueOf(empRow.get("mobile_num"))));

			userList.add(usermodel);

		}

		return userList;
	}

}
