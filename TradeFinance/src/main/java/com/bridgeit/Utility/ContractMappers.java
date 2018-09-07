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
		contractModel.setExporterId(rs.getString("importerId"));
		contractModel.setExporterId(rs.getString("importerBankId"));
		contractModel.setExporterId(rs.getString("insuranceId"));
		contractModel.setExporterId(rs.getString("customId"));
		contractModel.setPortOfLoadin(rs.getString("portOfLoading"));
		contractModel.setPortOfEntry(rs.getString("portOfEntry"));
		contractModel.setExporterCheck(rs.getBoolean("exporterCheck"));
		contractModel.setExporterCheck(rs.getBoolean("importerCheck"));
		contractModel.setExporterCheck(rs.getBoolean("importerBankCheck"));
		contractModel.setExporterCheck(rs.getBoolean("insuranceCheck"));
		contractModel.setExporterCheck(rs.getBoolean("customCheck"));

		return contractModel;
	}

}
