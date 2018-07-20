package com.bridgeit.lazyInitialized;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MainWithBeanFactory {
	public static void main(String[] args) {
		// Read the configuration file
		Resource resource = new ClassPathResource("LazyInitialized.xml");
		BeanFactory factory = new XmlBeanFactory(resource);
		System.out.println("loading bean only");

		// we have read our configurations with a BeanFactory, , we could see that
		// nothing happens just loading up of xml bean definition.
	}
}
