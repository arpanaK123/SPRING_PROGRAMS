package com.bridgeit.DAO;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
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

	public int inserContractData(TradeContractModel contract) throws SerialException, SQLException {

		String query = "insert into User_Contract (name, email,city,role,password,verified,Authentication_key,bankname,useraccount,accountnumber,balance) values (?,?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		Object[] args = new Object[] { contract.getContractId(), contract.getContentDescription(),
				contract.getContractMoney(), contract.getExporterId(), contract.getImporterId(),
				contract.getImporterBankId(), contract.getInsuranceId(), contract.getCustomId(),
				contract.isExporterCheck(), contract.isImporterCheck(), contract.isImporterBankCheck(),
				contract.isInsuranceCheck(), contract.isCustomCheck(), contract.getPortOfLoadin(),
				contract.getPortOfEntry() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Contract  saved ");
		} else {
			System.out.println("Contract save failed");
		}
		return out;
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

		Object[] args = { email };
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "select * from login where email=?";
		List<UserModel> user = jdbcTemplate.query(sql, args, new PersonMapper());
		UserModel user1 = user.get(0);
		return user1;
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

	public boolean checkUniqueAccountNumebr(UserModel useraccount) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { useraccount.getAccountnumber() };
		String query = "select accountnumber from login where accountnumber = ?";

		try {
			String name = jdbcTemplate.queryForObject(query, args, String.class);
			return true;
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

	public List<TradeContractModel> updateContract(String accountnumber) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		Object[] args = { accountnumber };
		String sql = "update login set contractId = ?,contentDescription=?,contractMoney=?, exporterId=?,importerId=?,importerBankId=?,insuranceId=?,customId=?,portOfLoadin=?,portOfEntry=?,exporterCheck=?,importerCheck=?,importerBankCheck=?,insuranceCheck=?,customCheck=? where contractId=?";
		List<TradeContractModel> contractList = jdbcTemplate.query(sql, args, new ContractMappers());

		return contractList;

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

	// public BaseResponse userReg(User user) {
	//
	// boolean outcome=dao.presence(user);
	// if(outcome!=true) {
	//
	// boolean isUniqueAccount = dao.uniqueAccountNumber(user.getAccounNumber());
	//
	// if (isUniqueAccount) {
	//
	// System.out.println("after presence");
	// String uuid=UUID.randomUUID().toString();
	// user.setAuthenticatedUserKey(uuid);
	// user.setVerified(true);
	// TradeUser admin = tradeUtil.getAdmin(caClient);
	// //convertUserAccountToByteArray(user.)
	// TradeUser userAcc = tradeUtil.makeTradeAccount(caClient, admin,
	// user.getAccounNumber(), user.getRole());
	// byte [] userAccountByte =tradeUtil.convertUserAccountToByteArray(userAcc);
	// user.setUserAccount(userAccountByte);
	//
	// try {
	// dao.insert(user);
	// } catch (SQLException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// Channel channel = tradeUtil.getChannel(client, admin);
	// System.out.println(channel.getName()+" is channel name");
	// int bal= user.getBalance();
	// String balance =Integer.toString(bal);
	// String [] args = new String[]
	// {user.getAccounNumber(),user.getRole(),balance,user.getBank()};
	// try {
	// tradeUtil.transactionInvokeBlockChain(client, "createAccount", args);
	// } catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {
	//
	// e.printStackTrace();
	// }
	// // channel.shutdown(true);
	// response.setCode(200);
	// response.setStatus(HttpStatus.OK);
	// response.setMessage("you are registered successfully...");
	// response.setErrors(null);
	//
	//

	// public boolean uniqueAccountNumber(String accountNumber) {
	//
	// Object [] args = {accountNumber};
	// String sql = "select * from UserLogin where account_number = ?";
	//
	// List<User> users = null;
	//
	// users = template.query(sql, args, new UserMapper());
	//
	// if(users.isEmpty()) {
	//
	// return true;
	// }
	//
	// return false;
	// }
	//
	// public boolean updateBalance(String accountNumber,int balance) {
	//
	// Object [] args = {balance,accountNumber};
	// String sql = "update UserLogin set balance = ? where account_number = ?";
	//
	// try {
	// int updatedRow = template.update(sql, args);
	// System.out.println(updatedRow+" row are affected..");
	// return true;
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// return false;
	// }
	//
	// }
	//
	// public byte [] getUserTradeAccount (String accountNumber) {
	// Object [] args = {accountNumber};
	//
	// String sql = "select user_account from UserLogin where account_number = ?";
	//
	// List<Blob> userBlobs =template.queryForList(sql, Blob.class, args);
	//
	// Blob userBlob =userBlobs.get(0);
	// byte [] userByte = null;
	// try {
	// userByte =userBlob.getBytes(1, (int)userBlob.length());
	// } catch (SQLException e) {
	//
	// e.printStackTrace();
	// }
	//
	// return userByte;
	// }
	//
	//
	// public boolean saveContract (Contract contract) {
	//
	// Object [] args =
	// {contract.getContractId(),contract.getContractDescription(),contract.getValue(),contract.getExporterId(),contract.getCustomId(),contract.getInsuranceId(),contract.getImporterId(),contract.getImporterBankId(),contract.getPortOfLoading(),contract.getPortOfEntry(),contract.isExporterCheck(),contract.isCustomCheck(),contract.isInsuranceCheck(),contract.isImporterCheck(),contract.isImporterBankCheck()};
	//
	// String sql ="insert into UserContract
	// (contract_id,contract_description,value,exporter_id,custom_id,insurance_id,importer_id,importerBank_id,port_of_loading,port_of_entry,exporterCheck,customCheck,insuranceCheck,importerCheck,importerBankCheck)
	// values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	//
	// try {
	// int row = template.update(sql, args);
	// System.out.println(row+" rows affected...");
	// return true;
	// } catch (DataAccessException e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }
	//
	// public boolean uniqueContract(String contractId) {
	//
	// Object [] args = {contractId};
	// String sql = "select * from UserContract where contract_id = ?";
	//
	// List<Contract> contractList = template.query(sql, args, new
	// ContractMapper());
	// if (contractList.isEmpty()) {
	// return true;
	// }
	//
	//
	// return false;
	// }
	//
	// public boolean updateContract(Contract contract) {
	//
	// Object [] args =
	// {contract.getContractDescription(),contract.getValue(),contract.getExporterId(),contract.getCustomId(),contract.getInsuranceId(),contract.getImporterId(),contract.getImporterBankId(),contract.getPortOfLoading(),contract.getPortOfEntry(),contract.isExporterCheck(),contract.isCustomCheck(),contract.isInsuranceCheck(),contract.isImporterCheck(),contract.isImporterBankCheck(),contract.getContractId()};
	// String sql= "update UserContract set contract_description =
	// ?,value=?,exporter_id=?,custom_id=?,insurance_id=?,importer_id=?,importerBank_id=?,port_of_loading=?,port_of_entry=?,exporterCheck=?,customCheck=?,insuranceCheck=?,importerCheck=?,importerBankCheck=?
	// where contract_id =?";
	//
	// try {
	// int rows = template.update(sql, args);
	// System.out.println(rows+" rows affected..");
	// return true;
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// return false;
	// }
	//
	//

}
