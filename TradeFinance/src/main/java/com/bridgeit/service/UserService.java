package com.bridgeit.service;

import java.io.IOException;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgeit.DAO.UserDAO;
import com.bridgeit.Utility.Consumer;
import com.bridgeit.Utility.GenerateTokens;
import com.bridgeit.Utility.Producer;
import com.bridgeit.model.UserModel;

@Service
public class UserService {
	//private DataSource dataSource;
	@Autowired
	UserDAO userDao;
	@Autowired
	GenerateTokens tokens;

	@Autowired
	Producer producer;
	@Autowired
	Consumer consumer;

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

	public boolean userReg(UserModel user) throws IOException {

		boolean status = userDao.presenceUser(user);
		if (status != true) {
			String uniqueID = UUID.randomUUID().toString();
			user.setAuthentication_key(uniqueID);
			userDao.inserData(user);
			long time = 20000;
			String userId = user.getId();
//			String tokngenerate = tokens.createVerificationToken(userId, user.getEmail(), time);
//			System.out.println("register user:"+userId);
//			System.out.println(tokngenerate);
			String sendThisUrl = "http://192.168.0.68:8080/user/generatetoken/";
			String sendMail=sendThisUrl;
			//consumer.sendMessage(sendMail);
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
	
	public UserModel getPersonByEmail(String email)
	{
		return userDao.getPersonByEmail(email);
	}
	
	
	public void userVerifiedById(UserModel userModel)
	{
		String id=userModel.getId();
		
	}

}
