package com.bridgeit.set;

public class User  implements Comparable<User>{
	int id;
	String name;
	Integer i;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String toString() {
		return "id: " + this.id + "name: " + this.name;
	}

	@Override
	public int compareTo(User arg0) {
		return 0;
	}
}
