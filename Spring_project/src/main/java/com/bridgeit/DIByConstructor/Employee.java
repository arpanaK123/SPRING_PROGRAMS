package com.bridgeit.DIByConstructor;

public class Employee {
	private int id;
	private String name;

	public Employee() {
		System.out.println("Employee");
	}

	public Employee(int id) {
		this.id = id;

	}

	public Employee(String name) {
		this.name = name;
	}

	public Employee(int id, String name) {
		this.id = id;
		this.name = name;

	}

	public void showInformation()

	{
		System.out.println("id: " + id + " \n" + "name: " + name);
	}

}
