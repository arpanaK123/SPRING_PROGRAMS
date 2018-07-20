package com.bridgeit.prototypeScope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("PrototypeBean.xml");
		Student student = (Student) context.getBean("student");
		System.out.println("student: " + student);
		student.setName("Aniket");
		System.out.println(student.getName());
		Student student1 = (Student) context.getBean("student");
		System.out.println("student1: " + student1);
		student1.setAddress("Mumbai");
		System.out.println(student1.getAddress());
		
	}
}
