package com.bridgeit.lazyInitialized;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Main {

	public static void main(String[] args) {
		Resource resource = new ClassPathResource("LazyInitialized.xml");
		BeanFactory factory = new XmlBeanFactory(resource);
		Bean1 bean1 = (Bean1) factory.getBean("bean1");
		Bean2 bean2 = (Bean2) factory.getBean("bean2");
		
		//the beans will be called.

	}

}
