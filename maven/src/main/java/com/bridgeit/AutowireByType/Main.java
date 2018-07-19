package com.bridgeit.AutowireByType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("ByType.xml");
		EmployeeBean employee = (EmployeeBean) context.getBean("employee");
		System.out.println(employee.getName());
		System.out.println(employee.getDepartment().getBook());

	}

}
