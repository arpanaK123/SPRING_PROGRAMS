package com.bridgeit.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
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
	TradeUser tradeUserAdmin;
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

	@Autowired
	Channel channel;

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
		boolean status = userDao.presenceUser(user);
		if (status != true) {
			boolean status1 = userDao.checkUniqueAccountNumebr(user.getAccountnumber());

			if (status1) {
				String uniqueID = UUID.randomUUID().toString();
				user.setAuthentication_key(uniqueID);
				user.setVerified(true);
				TradeUser tradeUserAccount = tradeFunction.getUserTradeAccount(caClient, tradeUserAdmin,
						user.getAccountnumber(), user.getRole());
				System.out.println("trade uSer " + tradeUserAdmin);
				System.out.println(tradeUserAdmin);
				byte[] userAccount = tradeFunction.getUserAccountToByteArray(tradeUserAccount);

				System.out.println("useraccount: " + userAccount);
				user.setUseraccount(userAccount);
				userDao.inserData(user);

				int bal = user.getBalance();
				String balance = Integer.toString(bal);
				System.out.println("channel name: " + channel.getName());
				String[] args = new String[] { user.getAccountnumber(), user.getRole(), balance, user.getBankname() };
				try {
					tradeFunction.invokeBlockChain(client, "create_Account", args, channel);
				} catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {

					e.printStackTrace();
				}
			}
		}
		return true;
	}

	//////

	public boolean userReg1(UserModel user) throws Exception {

		boolean status = userDao.presenceUser(user);
		if (status != true) {

			System.out.println("after presence");
			String uuid = UUID.randomUUID().toString();
			user.setAuthentication_key(uuid);
			user.setVerified(true);

			try {
				userDao.insertBeforeAcc(user);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			UserModel user1 = userDao.getUserByEmail(user.getEmail());
			String accountstring = user1.getId();
			user1.setAccountnumber(accountstring);
			TradeUser userAcc = tradeFunction.getUserTradeAccount(caClient, tradeUserAdmin, user1.getAccountnumber(),
					user1.getRole());
			byte[] userAccountByte = tradeFunction.getUserAccountToByteArray(userAcc);
			user1.setUseraccount(userAccountByte);

			try {
				userDao.updateUserAccountAndAccountNo(user1.getAccountnumber(), user1.getUseraccount(),
						user1.getEmail());
			} catch (SerialException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			System.out.println(channel.getName() + " is channel name");
			int bal = user.getBalance();
			String balance = Integer.toString(bal);
			String[] args = new String[] { user1.getAccountnumber(), user1.getRole(), balance, user1.getBankname() };
			try {
				tradeFunction.transactionInvokeBlockChain(client, "createAccount", args, channel);
			} catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {

				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean createContract(TradeContractModel contract) {
		// System.out.println("service: "+contract);
		contract.setExporterCheck(true);
		boolean status = userDao.saveContract(contract);

		if (status) {
			String value = Integer.toString(contract.getContractMoney());

			String[] args = { contract.getContractId(), contract.getContentDescription(), value,
					contract.getExporterId(), contract.getImporterId(), contract.getImporterBankId(),
					contract.getInsuranceId(), contract.getCustomId(), contract.getPortOfLoadin(),
					contract.getPortOfEntry() };
			try {

				tradeFunction.invokeBlockChain(client, "create_Contract", args, channel);

				return true;
			} catch (InvalidArgumentException e) {

				e.printStackTrace();
				return false;
			}

		}
		return false;
	}

	public boolean createContract(TradeContractModel contract, String jwtToken) {

		String email = tokens.getJwtId(jwtToken);

		UserModel user = userDao.getUserByEmail(email);
		System.out.println(user);
		if (user == null) {
			return false;
		}

		contract.setExporterCheck(true);
		;
		boolean insertion = userDao.saveContract(contract);

		if (insertion) {
			String value = Integer.toString(contract.getContractMoney());
			String[] args = { contract.getContractId(), contract.getContentDescription(), value,
					contract.getExporterId(), contract.getCustomId(), contract.getInsuranceId(),
					contract.getImporterId(), contract.getImporterBankId(), contract.getPortOfLoadin(),
					contract.getPortOfEntry() };
			try {

				tradeFunction.transactionInvokeBlockChain(client, "create_Contract", args, channel);

				return true;
			} catch (InvalidArgumentException e) {

				e.printStackTrace();
				return false;
			}

		}
		return false;

	}

	public TradeContractModel getContractByContractId(String contractId) {

		return userDao.getContractBy(contractId);

	}

	public TradeContractModel getContractResponse(String contractId) {

		TradeContractModel contract = userDao.getContract(contractId);

		return contract;
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

	public boolean userChangePassword(String userKey, String password) {
		String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		boolean result = userDao.userResetPassword(userKey, newPassword);
		return result;
	}

	public List<TradeContractModel> getAllContract(String jwt) {
		List<TradeContractModel> allContract = userDao.gellAllContract(user.getAccountnumber());
		return allContract;
	}

	public int getUserBalance(String accountNumber) {

		boolean result = userDao.uniqueAccountNumber(accountNumber);

		if (!result) {
			String[] args = new String[] { accountNumber };

			try {
				List<String> responses = tradeFunction.queryBlockChain(client, "get_Balance_By", args, channel);
				String response = responses.get(0);
				System.out.println(response + " is response balance");
				int balance = Integer.parseInt(response);
				userDao.updateBalance(accountNumber, balance);
				return balance;

			} catch (ProposalException e) {

				e.printStackTrace();
			} catch (InvalidArgumentException e) {

				e.printStackTrace();
			}
		}
		return -1;

	}

	public boolean updateContract(String jwtToken, TradeContractModel contract) throws InvalidArgumentException {

		boolean updatedInBc = updateContractInBlockChain(jwtToken, contract);

		if (updatedInBc) {

			boolean updatedInDb = updateContractInDB(contract.getContractId());

			if (updatedInDb) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public boolean updateContractInBlockChain(String jwtToken, TradeContractModel contract) {

		String email = tokens.getJwtId(jwtToken);
		System.out.println(email);
		UserModel user = userDao.getUserByEmail(email);
		System.out.println(user);

		String[] args = { user.getAccountnumber(), contract.getContractId() };
		System.out.println(user.getAccountnumber() + "---- " + contract.getContractId());
		switch (user.getRole()) {

		case "importer": {

			try {
				tradeFunction.transactionInvokeBlockChain(client, "accept_By_Importer", args, channel);
				return true;
			} catch (InvalidArgumentException e) {

				e.printStackTrace();
				return false;
			}

		}
		case "custom": {

			try {
				tradeFunction.transactionInvokeBlockChain(client, "accept_By_Custom", args, channel);
				return true;
			} catch (InvalidArgumentException e) {

				e.printStackTrace();
				return false;
			}

		}

		case "importerBank": {

			try {
				tradeFunction.transactionInvokeBlockChain(client, "accept_By_ImporterBank", args, channel);
				return true;
			} catch (InvalidArgumentException e) {

				e.printStackTrace();
				return false;
			}

		}
		case "insurance": {

			try {
				tradeFunction.transactionInvokeBlockChain(client, "accept_By_Insurance", args, channel);
				getUserBalance(contract.getExporterId());
				getUserBalance(contract.getImporterId());

				return true;
			} catch (InvalidArgumentException e) {

				e.printStackTrace();
				return false;
			}

		}

		}

		return false;
	}

	public boolean updateContractInDB(String contractId) throws InvalidArgumentException {

		String[] args = { contractId };
		ObjectMapper mapper = new ObjectMapper();
		List<String> responses = null;
		try {
			responses = tradeFunction.queryBlockChain(client, "get_Contract_By", args, channel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String response = responses.get(0);
		try {

			TradeContractModel contract = mapper.readValue(response, TradeContractModel.class);

			System.out.println(contract);

			boolean contractUpdated = userDao.updateContract(contract);

			if (contractUpdated) {

				return true;
			} else {
				return false;
			}

		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return false;
	}

	public TradeContractModel getContractFromBlockChain(String contractId, String jwtToken)
			throws InvalidArgumentException {

		String[] args = { contractId };
		ObjectMapper mapper = new ObjectMapper();
		TradeContractModel contract = null;
		try {
			List<String> responses = tradeFunction.queryBlockChain(client, "get_Contract_By", args, channel);
			String response = responses.get(0);
			try {

				contract = mapper.readValue(response, TradeContractModel.class);

			} catch (JsonParseException e) {

				e.printStackTrace();
			} catch (JsonMappingException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (ProposalException e) {
			e.printStackTrace();
		}

		return contract;
	}

}
