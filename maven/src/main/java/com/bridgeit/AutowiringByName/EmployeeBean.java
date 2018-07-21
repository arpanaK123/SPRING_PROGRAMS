package com.bridgeit.AutowiringByName;

public class EmployeeBean {
	private String fullName;
	private DepartmentBean departmentBean;

	public String getFullname() {
		return fullName;
	}

	public void setFullname(String fullname) {
		this.fullName = fullname;
	}

	public DepartmentBean getDepartmentBean() {
		return departmentBean;
	}

	public void setDepartmentBean(DepartmentBean departmentBean) {
		this.departmentBean = departmentBean;
	}

}
