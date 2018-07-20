package com.bridgeit.singletonScope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("SingletonBean.xml");
		HelloWorld helloworld = (HelloWorld) context.getBean("hello");
		System.out.println("helloWorld: " + helloworld);
		helloworld.setMessage("Hello Bridgelabz");
		System.out.println(helloworld.getMessage());
	    helloworld.setName("Aniket");
	    System.out.println(helloworld.getName());
		HelloWorld helloworld1 = (HelloWorld) context.getBean("hello");
		System.out.println("Helloworld1: " + helloworld1);
		System.out.println(helloworld1.getMessage());
		System.out.println(helloworld1.getName());

	}

}
