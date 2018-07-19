package com.bridgeit.AutowireByType;

public class EmployeeBean {
	private String name;
	DepartmentBean department;

	public DepartmentBean getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentBean department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
