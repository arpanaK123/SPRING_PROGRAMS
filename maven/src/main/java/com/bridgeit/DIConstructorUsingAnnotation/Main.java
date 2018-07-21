package com.bridgeit.DIConstructorUsingAnnotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println("1: "+context);
		Company company = context.getBean(Company.class);
		company.showEmployeeinfo();
		context.close();
	}

}
