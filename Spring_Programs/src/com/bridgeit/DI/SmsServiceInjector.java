package com.bridgeit.DI;

public class SmsServiceInjector implements MessageServiceInjector {

	@Override
	public Consumer getConsumer() {
		return (new MyDIApplication(new SmsServiceImple()));
	}

}
