package com.bridgeit.DAO;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.bridgeit.Utility.BcryptHash;
import com.bridgeit.Utility.ContractMappers;
import com.bridgeit.Utility.PersonMapper;
import com.bridgeit.model.TradeContractModel;
import com.bridgeit.model.UserModel;

@Repository
public class UserDAO {
	@Autowired
	private DataSource datasource;

	public int inserData(UserModel userModel) throws SerialException, SQLException {

		String query = "insert into login (name, email,city,role,password,verified,Authentication_key,bankname,useraccount,accountnumber,balance) values (?,?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = new Object[] { userModel.getName(), userModel.getEmail(), userModel.getCity(),
				userModel.getRole(), userModel.setPassword(BcryptHash.generatedHashPassword(userModel.getPassword())),
				userModel.isVerified(), userModel.getAuthentication_key(), userModel.getBankname(),
				new SerialBlob(userModel.getUseraccount()), userModel.getAccountnumber(), userModel.getBalance() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Employee saved ");
		} else {
			System.out.println("Employee save failed");
		}
		return out;
	}

	public boolean saveContract(TradeContractModel contract) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { contract.getContractId(), contract.getContentDescription(), contract.getContractMoney(),
				contract.getExporterId(), contract.getImporterId(), contract.getImporterBankId(),
				contract.getInsuranceId(), contract.getCustomId(), contract.getPortOfLoadin(),
				contract.getPortOfEntry(), contract.isExporterCheck(), contract.isImporterCheck(),
				contract.isImporterBankCheck(), contract.isInsuranceCheck(), contract.isCustomCheck(),contract.getBilloflading(),contract.getLetterofcredit() };

		String sql = "insert into User_Contract (contractId,contentDescription,contractMoney,exporterId,importerId,importerBankId,insuranceId,customId,portOfLoadin,portOfEntry,exporterCheck,importerCheck,importerBankCheck,insuranceCheck,customCheck,billoflading,letterofcredit) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			int row = jdbcTemplate.update(sql, args);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public TradeContractModel getContractBy(String contractId) {
		System.out.println(contractId);
		try {
			String fIND_PERSON = "select * from User_Contract where contractId = ?";
			Object[] args = { contractId };
			JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
			return jdbcTemplate.queryForObject(fIND_PERSON, args, new ContractMappers());
		} catch (Exception e) {
			return null;
		}
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

	public boolean updateUserAccountAndAccountNo(String accountNumber, byte[] Useraccount, String email)
			throws SerialException, SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { accountNumber, new SerialBlob(Useraccount), email };

		String sql = "update login set accountnumber = ? , useraccount = ? where email = ?";

		try {
			int row = jdbcTemplate.update(sql, args);
			System.out.println(row + " rows affected");
			return true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
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
		System.out.println("email:" + email);
		Object[] args = { email };
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "select * from login where email=?";
		List<UserModel> user = jdbcTemplate.query(sql, args, new PersonMapper());
		UserModel user1 = user.get(0);
		return user1;
	}

	public UserModel getUserByEmail(String email) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		System.out.println("email:" + email);
		Object[] args = { email };
		String sql = "select * from login where email = ?";
		List<UserModel> usersList = null;
		try {
			usersList = jdbcTemplate.query(sql, args, new PersonMapper());
		} catch (Exception e) {

			e.printStackTrace();
		}

		UserModel user = usersList.get(0);

		return user;

	}

	public boolean userResetPassword(String authentication_key, String newPassword) {
		Object[] args = { newPassword, authentication_key };

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

	public boolean checkUniqueAccountNumebr(String account_Number) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { account_Number };
		String sql = "select * from login where accountnumber=?";
		List<UserModel> user = jdbcTemplate.query(sql, args, new PersonMapper());
		if (user.isEmpty()) {
			return true;
		}

		return false;
	}

	public boolean checkContractPresent(String contractId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { contractId };
		String sql = "select * from User_Contract where contractId=?";
		List<TradeContractModel> contractList = jdbcTemplate.query(sql, args, new ContractMappers());
		if (contractList.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean updateContract(TradeContractModel contract) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = {contract.getContentDescription(), contract.getContractMoney(),
				contract.getExporterId(), contract.getImporterId(), contract.getImporterBankId(),
				contract.getInsuranceId(), contract.getCustomId(), contract.getPortOfLoadin(),
				contract.getPortOfEntry(), contract.isExporterCheck(), contract.isImporterCheck(),
				contract.isImporterBankCheck(), contract.isInsuranceCheck(), contract.isCustomCheck(),contract.isCompleteContract(),contract.getContractId()};
		String sql = "update User_Contract set contentDescription = ?,contractMoney=?,exporterId=?,importerId=?,importerBankId=?,insuranceId=?,customId=?,portofLoadin=?,portOfEntry=?,exporterCheck=?,importerCheck=?,importerBankCheck=?,insuranceCheck=?,customCheck=?,completeContract=? where contractId =?";

		try {
			int rows = jdbcTemplate.update(sql, args);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public List<UserModel> getBalanceByAccount(String accountnumber) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { accountnumber };
		String sql = "select balance from login where accountnumber=?";

		List<UserModel> user = jdbcTemplate.query(sql, args, new PersonMapper());

		return user;

	}

	public byte[] getUserAccount(String accountnumber) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { accountnumber };

		String sql = "select useraccount from login where accountnumber = ?";

		List<Blob> userBlobs = jdbcTemplate.queryForList(sql, Blob.class, args);
		Blob userBlob = userBlobs.get(0);
		byte[] userByte = null;

		userByte = userBlob.getBytes(1, (int) userBlob.length());
		return userByte;
	}

	public boolean updateUserAccountBalance(String accountNumber, int balance) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { balance, accountNumber };
		String sql = "update login set balance = ? where accountnumber = ?";

		try {
			int updatedRow = jdbcTemplate.update(sql, args);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	public boolean uniqueAccountNumber(String accountNumber) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { accountNumber };
		String sql = "select * from login where accountnumber = ?";

		List<UserModel> users = null;

		users = jdbcTemplate.query(sql, args, new PersonMapper());

		if (users.isEmpty()) {

			return true;
		}

		return false;
	}

	public boolean updateBalance(String accountNumber, int balance) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { balance, accountNumber };
		String sql = "update login set balance = ? where accountnumber = ?";

		try {
			int updatedRow = jdbcTemplate.update(sql, args);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	public byte[] getUserTradeAccount(String accountNumber) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { accountNumber };

		String sql = "select useraccount from login where accountnumber = ?";

		List<Blob> userBlobs = jdbcTemplate.queryForList(sql, Blob.class, args);

		Blob userBlob = userBlobs.get(0);
		byte[] userByte = null;
		try {
			userByte = userBlob.getBytes(1, (int) userBlob.length());
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return userByte;
	}

	public boolean uniqueContract(String contractId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { contractId };
		String sql = "select * from User_Contract where contractId = ?";

		List<TradeContractModel> contractList = jdbcTemplate.query(sql, args, new ContractMappers());
		if (contractList.isEmpty()) {
			return true;
		}

		return false;
	}

	public boolean copleteContract(String contractId) {
		System.out.println("competecontract: ");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { true, contractId };
		String sql = "update User_Contract set completeContract = ? where contractId =?";

		try {
			int rows = jdbcTemplate.update(sql, args);
			System.out.println("updated");
			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

	}

	public TradeContractModel getContract(String contractId) {

		Object[] args = { contractId };

		String sql = "select * from User_Contract where contractId = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		List<TradeContractModel> contractList = jdbcTemplate.query(sql, args, new ContractMappers());
		if (contractList.isEmpty()) {
			return null;
		}

		TradeContractModel contract = contractList.get(0);

		return contract;
	}

	public List<TradeContractModel> gellAllContract(String userId, String role) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = { userId };
		String sql = null;

		switch (role) {

		case "exporter": {

			sql = "select * from User_Contract where exporterId = ?";
			break;
		}
		case "custom": {
			sql = "select * from User_Contract where customId = ?";
			break;

		}

		case "insurance": {
			sql = "select * from User_Contract where insuranceId = ?";
			break;

		}

		case "importer": {

			sql = "select * from User_Contract where importerId = ?";
			break;

		}

		case "importerBank": {

			sql = "select * from User_Contract where importerBankId = ?";
			break;

		}

		default: {

			break;
		}

		}

		List<TradeContractModel> contractList = null;
		try {

			contractList = jdbcTemplate.query(sql, args, new ContractMappers());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return contractList;
	}

	public boolean insertBeforeAcc(UserModel user) throws SerialException, SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { user.getEmail(), user.getName(),
				user.setPassword(BcryptHash.generatedHashPassword(user.getPassword())), user.getRole(),
				user.isVerified(), user.getAuthentication_key(), user.getBalance(), user.getBankname() };
		int out = 0;
		try {
			System.out.println(user);
			out = jdbcTemplate.update(
					"insert into login(email,name,password,city,role,verified,authenticated_key,balance,bankname) values (?,?,?,?,?,?,?,?,?)",
					args);
			System.out.println("number rows affected " + out);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("insert template not excuted..");
			return false;
		}

	}

}
