package com.bridgeit.postConstructPreDestroy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("Annotation.xml");

		CustomerService customerService = (CustomerService) context.getBean("customerService");

		context.close();
	}
}
