package com.bridgeit.DIConstructorUsingAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Company {

	private Employee employee;

	@Autowired
	public Company(Employee employee) {
		this.employee = employee;
	}

	public void showEmployeeinfo() { 
		System.out.println("company");
		employee.showEmployeeinfo();
	}
}
