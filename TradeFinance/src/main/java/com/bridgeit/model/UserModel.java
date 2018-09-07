package com.bridgeit.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@NotEmpty(message = "name is required")
	private String name;

	@NotEmpty(message = "city is required")
	private String city;
	@NotEmpty(message = "role is required")
	private String role;

	@Email(message = "email is invalid")
	@NotEmpty(message = "email is required")
	@Column(unique = true)
	private String email;

	@NotEmpty(message = "bank name is required")
	private String bankname;
	private String accountnumber;

	private byte[] useraccount;

	private int balance;

	String authentication_key;

	private String password;

	private boolean verified;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public Object setPassword(String password) {
		return this.password = password;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getAuthentication_key() {
		return authentication_key;
	}

	public void setAuthentication_key(String authentication_key) {
		this.authentication_key = authentication_key;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public byte[] getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(byte[] useraccount) {
		this.useraccount = useraccount;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	

}
