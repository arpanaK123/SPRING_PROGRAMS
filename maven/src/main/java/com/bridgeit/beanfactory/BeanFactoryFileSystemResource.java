package com.bridgeit.beanfactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class BeanFactoryFileSystemResource {
	public static void main(String[] args) {
		FileSystemResource resource = new FileSystemResource("src/main/resources/BeanFactory.xml");
		System.out.println(resource);
		BeanFactory factory = new XmlBeanFactory(resource);
		System.out.println(factory);
		Employee employee = (Employee) factory.getBean("employee");
		System.out.println(employee.getId() + " " + employee.getName());
	}
}
