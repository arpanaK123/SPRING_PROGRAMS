package com.bridgeit.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.DAO.UserDAO;
import com.bridgeit.model.UserModel;

@Service
public class UserService {
	private DataSource dataSource;
	@Autowired
	UserDAO userDao;

	public void callToUserdDAO(UserModel userModel)

	{
		userDao.inserData(userModel);
	}

	public boolean loginUser(String email, String password) {

		boolean status = userDao.checkUser(email, password);
		if (status) {
			return true;
		} else {
			return false;
		}

	}

	public boolean userReg(UserModel user) {

		boolean status = userDao.presenceUser(user);
		if (status != true) {
			userDao.inserData(user);

			return true;
		}
		return false;
	}

	public boolean login(String email, String password) {

		boolean status = userDao.checkUser(email, password);
		if (status) {
			return true;
		}
		return false;
	}

}
