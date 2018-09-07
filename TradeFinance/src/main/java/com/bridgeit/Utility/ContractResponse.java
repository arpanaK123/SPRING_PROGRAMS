package com.bridgeit.Utility;

import com.bridgeit.model.TradeContractModel;

public class ContractResponse {
	private String status;
	private String statusCode;
	private TradeContractModel contractModel;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public TradeContractModel getContractModel() {
		return contractModel;
	}

	public void setContractModel(TradeContractModel contractModel) {
		this.contractModel = contractModel;
	}

}
