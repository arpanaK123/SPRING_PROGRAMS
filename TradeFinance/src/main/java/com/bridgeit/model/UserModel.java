package com.bridgeit.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserModel {

	@NotEmpty(message = "name is required")
	private String name;

	@NotEmpty(message = "email is required")
	@Email
	private String email;
	@NotEmpty(message = "mobile number  is required")
	private String mobilenumber;
	@NotEmpty(message = "city is required")
	private String city;
	@NotEmpty(message = "role is required")
	private String role;

	public UserModel() {
	}

	public UserModel(String name, String email, String mobilenumber, String city, String role) {
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

	public String toString() {
		return "name: " + name + " email: " + email + " mobnum: " + mobilenumber + " city: " + city + " role: " + role;
	}
}
