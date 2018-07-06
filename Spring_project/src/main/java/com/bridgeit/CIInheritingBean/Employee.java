package com.bridgeit.CIInheritingBean;

public class Employee {
	private int id;
	private String name;
	private Address address;

	public Employee() {
	}

	public Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Employee(int id, String name, Address address)

	{
		this.id = id;
		this.name = name;
		this.address = address;

	}

	public void shoe() {
		System.out.println("id:" + id + " " + "name:" + name);
		System.out.println("Address:" + address);
	}
}
