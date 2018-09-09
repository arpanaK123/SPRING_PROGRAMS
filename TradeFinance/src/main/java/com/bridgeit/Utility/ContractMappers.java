package com.bridgeit.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bridgeit.model.TradeContractModel;

public class ContractMappers implements RowMapper<TradeContractModel> {

	@Override
	public TradeContractModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		TradeContractModel contractModel = new TradeContractModel();
		contractModel.setContractId(rs.getString("contractId"));
		contractModel.setContentDescription(rs.getString("contentDescription"));
		contractModel.setContractMoney(rs.getInt("contractMoney"));

		contractModel.setExporterId(rs.getString("exporterId"));
		contractModel.setImporterId(rs.getString("importerId"));
		contractModel.setImporterBankId(rs.getString("importerBankId"));
		contractModel.setInsuranceId(rs.getString("insuranceId"));
		contractModel.setCustomId(rs.getString("customId"));
		contractModel.setPortOfLoadin(rs.getString("portOfLoadin"));
		contractModel.setPortOfEntry(rs.getString("portOfEntry"));
		contractModel.setExporterCheck(rs.getBoolean("exporterCheck"));
		contractModel.setImporterCheck(rs.getBoolean("importerCheck"));
		contractModel.setImporterBankCheck(rs.getBoolean("importerBankCheck"));
		contractModel.setInsuranceCheck(rs.getBoolean("insuranceCheck"));
		contractModel.setCustomCheck(rs.getBoolean("customCheck"));

		return contractModel;
	}

}
