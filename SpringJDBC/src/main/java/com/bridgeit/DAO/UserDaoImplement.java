package com.bridgeit.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import com.bridgeit.model.UserModel;

public class UserDaoImplement implements UserDAO {

	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public void saveUserData(UserModel user) {
		String query = "insert  into employee(id,name, email, password, mobile_num, address) values(?,?,?,?,?,?) ";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = datasource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getMobile_num());
			statement.setString(6, user.getAddress());

			int data = statement.executeUpdate();
			if (data != 0) {
				System.out.println("User Data saved by id: " + user.getId());
			} else {
				System.out.println("User Data failed by id: " + user.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public UserModel userGetById(int id) {

		String query = "select name, email, password, mobile_num, address from employee where id='2'";
		UserModel userModel = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = datasource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeQuery();
			if (result.next()) {
				userModel = new UserModel();
			//	userModel.setId("id");
				userModel.setName(result.getString("name"));
				userModel.setEmail(result.getString("email"));
				userModel.setPassword(result.getString("password"));
				userModel.setMobile_num(result.getString("mobile_num"));
				userModel.setAddress(result.getString("address"));
				System.out.println("User Data found: " + userModel);

			} else {
				System.out.println("User data not found by Id: " + userModel.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				result.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return userModel;

	}

	public void updateUserData(UserModel user) {
		String query = "update employee set name=?, email=?, password=?, mobile_num=?, address=? where id=1";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = datasource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getMobile_num());
			statement.setString(5, user.getAddress());
			// statement.setInt(6, user.getId());
			int data = statement.executeUpdate();
			if (data != 0) {
				System.out.println("user data update with id ");
			} else {
				System.out.println("user update failed with id ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void deleteUserById(int id) {
		String query = "delete from employee where id=?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = datasource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			int data = statement.executeUpdate();
			if (data != 0) {
				System.out.println("user data deleted by id: " + id);
			} else {
				System.out.println("user data not found with id: " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public List<UserModel> getAll() {
		String query = "select id,name, email, password, mobile_num, address from employee";
		List<UserModel> userList = new ArrayList<UserModel>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = datasource.getConnection();
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			while (result.next()) {
				UserModel userModel = new UserModel();
				userModel.setId(result.getInt("id"));
				userModel.setName(result.getString("name"));
				userModel.setEmail(result.getString("email"));
				userModel.setPassword(result.getString("password"));
				userModel.setMobile_num(result.getString("mobile_num"));
				userModel.setAddress(result.getString("address"));
				userList.add(userModel);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userList;
	}

}
