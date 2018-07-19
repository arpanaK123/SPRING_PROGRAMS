package com.bridgeit.AutowireByConstructor;

public class EmployeeBean {
	private String fullname;
	private DepartmentBean departmentbean;

	
	public EmployeeBean( DepartmentBean departmentbean) {
		super();
		this.departmentbean = departmentbean;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public DepartmentBean getDepartmentbean() {
		return departmentbean;
	}

	public void setDepartmentbean(DepartmentBean departmentbean) {
		this.departmentbean = departmentbean;
	}

}
