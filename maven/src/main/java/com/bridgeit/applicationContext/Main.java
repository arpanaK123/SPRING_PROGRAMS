package com.bridgeit.applicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src/main/resources/ApplicationContextBean.xml");
		
		//ApplicationContext context1=new WebXmlApplicationContext("")
		Employee employee = (Employee) context.getBean("employee");
		System.out.println(employee.getId() + " " + employee.getName());

	}
}
