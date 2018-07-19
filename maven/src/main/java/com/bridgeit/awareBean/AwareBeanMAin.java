package com.bridgeit.awareBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AwareBeanMAin {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("AwareBean.xml");
		AwareBean bean = (AwareBean) context.getBean("AwareBean");
		((AbstractApplicationContext) context).registerShutdownHook();

	}
}
