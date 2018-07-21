package com.bridgeit.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("HelloWorldAnnotation.xml");
		HelloWorld helloworld = (HelloWorld) context.getBean("myBean");
		helloworld.sayHello();
	}

}
