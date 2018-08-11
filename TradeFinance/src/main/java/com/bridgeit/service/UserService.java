package com.bridgeit.service;

import java.io.IOException;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.bridgeit.DAO.UserDAO;
import com.bridgeit.Utility.Consumer;
import com.bridgeit.Utility.GenerateTokens;
import com.bridgeit.Utility.Producer;
import com.bridgeit.Utility.UserMail;
import com.bridgeit.model.UserModel;

@Service
public class UserService {
	@Autowired
	UserDAO userDao;
	@Autowired
	GenerateTokens tokens;

	@Autowired
	Producer producer;
	@Autowired
	Consumer consumer;
	@Autowired
	UserModel user;

	@Autowired
	UserMail mail;

	@Autowired
	Producer mailsender;

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

	public UserModel getPersonByEmail(String email) {
		return userDao.getPersonByEmail(email);
	}

	public void userVerifiedById(UserModel userModel) {
		String id = userModel.getId();

	}

	public UserModel getUserByUniqueKey(String authentication_key) {
		return userDao.getUserByUniqueKey(authentication_key);
	}

	public boolean update(UserModel usermodel) {
		boolean updateUser = userDao.update(usermodel);
		if (updateUser) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isVerifiedUser(String email) {
		UserModel user = userDao.getPersonByEmail(email);
		return user.isVerified();
	}

	public boolean userCheckByKey(String authentication_key) {

		boolean status = userDao.checkUserForResetpassword(authentication_key);
		if (status) {

			return true;
		}
		return false;

	}

	public boolean verify(String authentication_key) {

		boolean result = userDao.getVerified(authentication_key);

		return result;

	}

	public UserModel getUser(String email) {

		UserModel user = userDao.fetchUserByEmail(email);
		return user;
	}

	public String getToken(UserModel user) {

		String userId = user.getId();
		String jwtToken = tokens.createVerificationToken(userId, user.getEmail(), 720000);

		return jwtToken;
	}

//	public boolean userChangePassword(String authentication_key, String password) {
//		String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//		boolean result = userDao.userResetPassword(authentication_key, newPassword);
//
//		return result;
//	}

	public boolean userChangePassword(String userKey,String password) {
		String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		boolean result = userDao.userResetPassword(userKey, newPassword);
		return result;
	}
	
}
