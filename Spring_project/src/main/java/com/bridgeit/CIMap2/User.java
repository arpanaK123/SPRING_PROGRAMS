package com.bridgeit.CIMap2;

public class User {
	private int id;
	private String email, name;

	public User() {
	}

	public User(int id, String email, String name) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
	}

	public String toString() {
		return "id: " + id + " email: " + name + " name: " + email;
	}
}
