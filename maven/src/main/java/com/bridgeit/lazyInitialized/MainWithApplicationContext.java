package com.bridgeit.lazyInitialized;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainWithApplicationContext {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("LazyInitialized.xml");
		System.out.println("context: " + context);

		// itself bean will be getting loaded. Even no need to call.

		// after doing lazy-init="true" in xml file
		// class beans will not get loaded unless and until called explicitly, as you
		// can see the constructor is not called.
	}

}
