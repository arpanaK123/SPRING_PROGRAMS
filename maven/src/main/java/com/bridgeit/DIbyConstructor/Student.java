package com.bridgeit.DIbyConstructor;

public class Student {
	private String id, name;

	public Student(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void show() {
		System.out.println("Student: [id: " + id + "  name: " + name + " ]");
	}
}
