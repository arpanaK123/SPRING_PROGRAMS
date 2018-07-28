package com.bridgeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.DAO.UserDAO;
import com.bridgeit.model.UserModel;

@Service
public class UserService {
	
	@Autowired
	UserDAO userDao;	

	public void callToUserdDAO(UserModel userModel)
	{
		userDao.inserData(userModel);
	}

	


}
