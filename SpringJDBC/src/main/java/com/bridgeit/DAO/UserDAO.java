package com.bridgeit.DAO;

import java.util.List;

import com.bridgeit.model.UserModel;

public interface UserDAO {
	public void saveUserData(UserModel user);

	public UserModel userGetById(int id);

	public void updateUserData(UserModel user);

	public void deleteUserById(int id);

	public List<UserModel> getAll();
};
