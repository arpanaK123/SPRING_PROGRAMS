package com.bridgeit.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {
	private static final long serialVersionUID = 1l;
	private String id;
	private String name;
	private BigDecimal balance;

	public Account(String id, String name, BigDecimal balance) {
		super();
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return "id:" + id + "name: " + name + " balance: " + balance;
	}

}
