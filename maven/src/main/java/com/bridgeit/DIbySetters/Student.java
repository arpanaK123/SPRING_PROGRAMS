package com.bridgeit.DIbySetters;

public class Student {
	private String id, name, address;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void show() {
		System.out.println("Student: [id: " + id + "  name: " + name + "  address: " + address+"]");
	}
}
