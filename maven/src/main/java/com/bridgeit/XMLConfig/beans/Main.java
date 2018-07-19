package com.bridgeit.XMLConfig.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("XMLBean.xml");

		Employee employee = context.getBean(Employee.class);
		System.out.println(employee);

		Department department = context.getBean(Department.class);
		System.out.println(department);

		Operation operation = context.getBean(Operation.class);
		operation.helloWorld();
	}

}
