package com.bridgeit.DISettersUsingAnnotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Company company = context.getBean(Company.class);
		company.showDepartmentInfo();
		context.close();
		//System.out.println("close");

	}

}
