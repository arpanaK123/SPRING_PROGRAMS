package com.bridgeit.model;

public class UserPoso {
	private String First_Name;
	private String Last_Name;
	private String User_Name;
	private String PassWord;
	private String Mobile_Number;

	public UserPoso() {

		this.First_Name = First_Name;
		this.Last_Name = Last_Name;
		this.User_Name = User_Name;
		this.PassWord = PassWord;
		this.Mobile_Number = Mobile_Number;
	}

	

	public String getFirst_Name() {
		return First_Name;
	}



	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}



	public String getLast_Name() {
		return Last_Name;
	}



	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}



	public String getEmail() {
		return User_Name;
	}



	public void setEmail(String email) {
		User_Name = email;
	}



	public String getPassWord() {
		return PassWord;
	}



	public void setPassWord(String passWord) {
		PassWord = passWord;
	}



	public String getMobile_Number() {
		return Mobile_Number;
	}



	public void setMobile_Number(String mobile_Number) {
		Mobile_Number = mobile_Number;
	}



	public String toString() {
		return "First_Name: " + First_Name + " " + "LastName: " + Last_Name + " " + "Mobile_num: " + Mobile_Number;
	}

}
