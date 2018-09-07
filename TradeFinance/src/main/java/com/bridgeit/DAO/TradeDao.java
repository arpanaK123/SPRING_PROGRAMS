package com.bridgeit.DAO;

public class TradeDao {
	
//public boolean uniqueAccountNumber(String accountNumber) {
//		
//		Object [] args = {accountNumber};
//		String sql = "select * from UserLogin where account_number = ?";
//		
//		 List<User> users = null;
//		 
//		 users = template.query(sql, args, new UserMapper());
//		 
//		 if(users.isEmpty()) {
//			 
//			 return true;
//		 }
//		
//		return false;
//	}
//	
//	public boolean  updateBalance(String accountNumber,int balance) {
//		
//		Object [] args = {balance,accountNumber};
//		String sql = "update UserLogin set balance = ? where account_number = ?";
//		
//		try {
//			int updatedRow = template.update(sql, args);
//			System.out.println(updatedRow+" row are affected..");
//			return true;
//		} catch (Exception e) {
//		
//			e.printStackTrace();
//			return false;
//		}
//
//	}
//	
//	public byte []  getUserTradeAccount (String accountNumber) {
//		Object [] args = {accountNumber};
//		
//		String sql = "select user_account from UserLogin where account_number = ?";
//		
//		List<Blob> userBlobs =template.queryForList(sql, Blob.class, args);
//		
//		Blob userBlob =userBlobs.get(0);
//		byte [] userByte = null;
//		try {
//			userByte =userBlob.getBytes(1, (int)userBlob.length());
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		
//		return userByte;
//	}
//	
//	
//	public boolean saveContract (Contract contract) {
//		
//		Object [] args = {contract.getContractId(),contract.getContractDescription(),contract.getValue(),contract.getExporterId(),contract.getCustomId(),contract.getInsuranceId(),contract.getImporterId(),contract.getImporterBankId(),contract.getPortOfLoading(),contract.getPortOfEntry(),contract.isExporterCheck(),contract.isCustomCheck(),contract.isInsuranceCheck(),contract.isImporterCheck(),contract.isImporterBankCheck()};
//		
//		String sql ="insert into UserContract (contract_id,contract_description,value,exporter_id,custom_id,insurance_id,importer_id,importerBank_id,port_of_loading,port_of_entry,exporterCheck,customCheck,insuranceCheck,importerCheck,importerBankCheck) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		
//		try {
//			int row = template.update(sql, args);
//			System.out.println(row+" rows affected...");
//			return true;
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		}
//
//		return false;
//	}
//	
//	public boolean uniqueContract(String contractId) {
//		
//		Object [] args = {contractId};
//		String sql = "select * from UserContract where contract_id = ?";
//		
//		List<Contract> contractList = template.query(sql, args, new ContractMapper());
//		if (contractList.isEmpty()) {	
//			return true;
//		}
//		
//		
//		return false;
//	}
//	
//	public boolean updateContract(Contract contract) {
//		
//		Object [] args = {contract.getContractDescription(),contract.getValue(),contract.getExporterId(),contract.getCustomId(),contract.getInsuranceId(),contract.getImporterId(),contract.getImporterBankId(),contract.getPortOfLoading(),contract.getPortOfEntry(),contract.isExporterCheck(),contract.isCustomCheck(),contract.isInsuranceCheck(),contract.isImporterCheck(),contract.isImporterBankCheck(),contract.getContractId()};
//		String sql= "update UserContract set contract_description = ?,value=?,exporter_id=?,custom_id=?,insurance_id=?,importer_id=?,importerBank_id=?,port_of_loading=?,port_of_entry=?,exporterCheck=?,customCheck=?,insuranceCheck=?,importerCheck=?,importerBankCheck=? where contract_id =?";
//		
//		try {
//			int rows = template.update(sql, args);
//			System.out.println(rows+" rows affected..");
//			return true;
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//
//		return false;
//	}
//	
	
}
