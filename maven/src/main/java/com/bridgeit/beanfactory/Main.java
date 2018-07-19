package com.bridgeit.beanfactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Main {

	public static void main(String[] args) {

		Resource resource = new ClassPathResource("BeanFactory.xml");
		System.out.println("resource: " + resource);
		BeanFactory factory = new XmlBeanFactory(resource);
		System.out.println("beanfactory: " + factory);
		Employee employee = (Employee) factory.getBean("employee");
		System.out.println(employee.getId() + " " + employee.getName());
	}

}
