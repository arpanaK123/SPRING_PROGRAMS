package com.bridgeit.exception;

public class ErrorDetails {
	private String name;
	private String email;
	private String mobilenumber;
	private String city;
	private String role;

	public ErrorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorDetails(String name, String email, String mobilenumber, String city, String role) {
		super();
		this.name = name;
		this.email = email;
		this.mobilenumber = mobilenumber;
		this.city = city;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
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

}
