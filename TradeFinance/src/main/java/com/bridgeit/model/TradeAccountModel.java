package com.bridgeit.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class TradeAccountModel {

	@NotEmpty(message = "acc. no. is required")
	private String accountNumber;
	
	@NotEmpty(message = "acc. holder name is required")
	private String accountHolderName;
	
	@NotEmpty(message = "acc. Balance. is required")
	private int accountBalance;
	
	@NotEmpty(message = "bank Name is required")
	private String bankName;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public int getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
