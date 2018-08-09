package com.bridgeit.Utility;

import com.bridgeit.model.UserModel;

public class ResponseError {
	private String status;
	private String statusCode;
	private UserModel usermodel;

	private String generateTokens;
	
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

	public UserModel getUsermodel() {
		return usermodel;
	}

	public void setUsermodel(UserModel usermodel) {
		this.usermodel = usermodel;
	}

	public String getGenerateTokens() {
		return generateTokens;
	}

	public void setGenerateTokens(String generateTokens) {
		this.generateTokens = generateTokens;
	}

	

}
