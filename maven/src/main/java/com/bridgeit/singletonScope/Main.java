package com.bridgeit.singletonScope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		ApplicationContext context=new ClassPathXmlApplicationContext("SingletonBean.xml");
		HelloWorld hellodorld=(HelloWorld) context.getBean("hello");
		System.out.println(hellodorld.getMessage());
		System.out.println(hellodorld.getName());
	}

}
