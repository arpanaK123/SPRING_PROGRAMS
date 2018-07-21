package com.bridgeit.springLifeCycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersonBeanMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("LifeCycle.xml");
		PersonBean obj = (PersonBean) context.getBean("personBean");
		System.out.println(obj.getName());
//	((AbstractApplicationContext) context).registerShutdownHook();
context.close();
	}

}
