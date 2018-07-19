package com.bridgeit.AutowiringByName;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("ByName.xml");
		EmployeeBean employee = (EmployeeBean) context.getBean("employee");
		System.out.println(employee.getFullname());
		System.out.println(employee.getDepartmentBean().getName());
	}

}
