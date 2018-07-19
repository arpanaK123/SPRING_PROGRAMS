package com.bridgeit.XMLConfig.beans;

public class Department {
	private String id;
	private String name;

	public Department() {
		super();
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

	public String toString() {
		return "Department: [id: " + id + "  name: " + name + " ]";
	}
}
