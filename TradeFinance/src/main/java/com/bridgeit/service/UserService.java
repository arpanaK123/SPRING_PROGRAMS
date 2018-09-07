package com.bridgeit.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.bridgeit.DAO.UserDAO;
import com.bridgeit.Utility.Consumer;
import com.bridgeit.Utility.GenerateTokens;
import com.bridgeit.Utility.Producer;
import com.bridgeit.Utility.TradeFunctionUtility;
import com.bridgeit.Utility.TradeUtility;
import com.bridgeit.Utility.UserMail;
import com.bridgeit.model.TradeContractModel;
import com.bridgeit.model.TradeUser;
import com.bridgeit.model.UserModel;
import com.bridgeit.model.Usermodel;

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
	TradeUser tradeUser;
	@Autowired
	UserMail mail;

	@Autowired
	Producer mailsender;
	@Autowired
	HFCAClient caClient;

	@Autowired
	HFClient client;

	@Autowired
	TradeFunctionUtility tradeFunction;

//	@Autowired
//	Channel channel;

	public void callToUserdDAO(UserModel userModel) throws SerialException, SQLException

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

	public boolean userReg(UserModel user) throws Exception {
		 boolean status1 = userDao.checkUniqueAccountNumebr(user);
		//boolean status = userDao.presenceUser(user);
		if (status1 != true) {
			String uniqueID = UUID.randomUUID().toString();
			user.setAuthentication_key(uniqueID);
			user.setVerified(true);
			TradeUser admin = tradeFunction.getAdmin(caClient);
			TradeUser tradeUserAccount = tradeFunction.getUserTradeAccount(caClient, admin, user.getAccountnumber(),
					user.getRole());
			System.out.println("trade uSer " + tradeUser);
			System.out.println(admin);
			byte[] userAccount = tradeFunction.getUserAccountToByteArray(tradeUserAccount);
			System.out.println("useraccount: " + userAccount);
			user.setUseraccount(userAccount);
			userDao.inserData(user);
			Channel channel = tradeFunction.getChannel(client, admin);
			// channel(client, admin);
			System.out.println("channel name: " + channel.getName());
			tradeFunction.ivokeBlockChain(client, user, "create_Account");

			// return true;
		}
		return true;
	}

	public boolean createContract(TradeContractModel contractModel) throws Exception {
		try {
			
			TradeUser admin = tradeFunction.getAdmin(caClient);
			contractModel.setExporterCheck(true);
			TradeUser tradeUserAccount = tradeFunction.getUserTradeContract(caClient, admin, contractModel.getContractId());


			userDao.inserContractData(contractModel);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	public boolean getContractByContractId(String contractId) {
		boolean status = userDao.checkContractPresent(contractId);
		if (status) {
			return true;
		}
		return false;

	}

	public boolean getAccountBy(String accountnumber) {
		boolean status = userDao.checkUniqueAccountNumebr(accountnumber);
		if (status) {
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

	// public boolean userChangePassword(String authentication_key, String password)
	// {
	// String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	// boolean result = userDao.userResetPassword(authentication_key, newPassword);
	//
	// return result;
	// }

	public boolean userChangePassword(String userKey, String password) {
		String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		boolean result = userDao.userResetPassword(userKey, newPassword);
		return result;
	}

	public int getBalanceBy(String account_Number) throws Exception {
		boolean status = userDao.checkUniqueAccountNumebr(account_Number);
		if (status == false) {
			TradeUser admin = tradeFunction.getAdmin(caClient);

			tradeFunction.getChannel(client, admin);
			String[] arrayargs = new String[] { account_Number };
			String userfunction = null;
			// tradeFunction.queryBlockChain(client, userfunction, arrayargs);
			List<String> list = tradeFunction.queryBlockChain(client, "getBalanceBy", arrayargs);
			String traderesponse = list.get(0);
			System.out.println("response: " + traderesponse);
			int accountBalance = Integer.parseInt(traderesponse);
			userDao.updateUserAccountBalance(account_Number, accountBalance);
			return accountBalance;
		}

		return -1;
	}

	public boolean UpdateContractByContractId( String contractId) {
		boolean status=userDao.checkContractPresent(contractId);
		if(status)
		{
			userDao.updateContract(contractId);
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	////////
	// public int getUserBalance(String accountNumber) {
	//
	// boolean result = dao.uniqueAccountNumber(accountNumber);
	//
	// if (!result) {
	// TradeUser admin = tradeUtil.getAdmin(caClient);
	//// byte [] tradeUserByte =dao.getUserTradeAccount(accountNumber);
	//// TradeUser tradeUser = tradeUtil.convertByteArrayToObject(tradeUserByte);
	// // System.out.println(tradeUser);
	// tradeUtil.getChannel(client, admin);
	// //tradeUtil.queryBlockChain(client, function, args);
	// String [] args = new String[] {accountNumber};
	// // ObjectMapper mapper = new ObjectMapper();
	// // mapper.
	// try {
	// List<String> responses = tradeUtil.queryBlockChain(client, "getBalance",
	// args);
	// String response = responses.get(0);
	// System.out.println(response +" is response for query");
	//
	// int balance = Integer.parseInt(response);
	//
	// dao.updateBalance(accountNumber, balance);
	// return balance ;
	//
	// } catch (ProposalException e) {
	//
	// e.printStackTrace();
	// } catch (InvalidArgumentException e) {
	//
	// e.printStackTrace();
	// }
	// }
	// return -1;
	//

}
