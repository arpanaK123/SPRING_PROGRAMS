package com.bridgeit.awareBean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AwareBeanMAin {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AwareBean.xml");
		AwareBean bean = (AwareBean) context.getBean("AwareBean");
		// ((AbstractApplicationContext) context).registerShutdownHook();
		context.close();
	}
}
