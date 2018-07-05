package com.bridgeit.CIDependentObject;

public class Employee {

	private int id;
	private String name;
	private Address address;

	public Employee() {
		System.out.println("employee");
	}

	public Employee(int id, String name, Address address) {
		this.id = id;
		this.name = name;
		this.address = address;

	}

	public void showInformation() {
		System.out.println("id: " + id + " " + "name: " + name);
		System.out.println(address.toString());
	}
}
