package com.bridgeit.model;

import java.util.List;

import org.apache.http.HttpStatus;

public class ContractLists {
	private List<TradeContractModel> contracts;

	private String status;
	private int code;

	public List<TradeContractModel> getContracts() {
		return contracts;
	}

	public void setContracts(List<TradeContractModel> contracts) {
		this.contracts = contracts;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
