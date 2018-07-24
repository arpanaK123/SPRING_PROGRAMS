package com.bridgeit.customInitDestroy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainCustomLifeCycyle {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("CustomLifeCycle.xml");
		CustomLifeCycleBean custom = (CustomLifeCycleBean) context.getBean("customLifeCycyleBean");
		System.out.println(custom.getName());
		((AbstractApplicationContext) context).registerShutdownHook();

	}
}
