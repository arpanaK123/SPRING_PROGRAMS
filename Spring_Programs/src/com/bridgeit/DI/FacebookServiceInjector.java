package com.bridgeit.DI;

public class FacebookServiceInjector implements MessageServiceInjector {

	@Override
	public Consumer getConsumer() {
		// TODO Auto-generated method stub
		return (new MyDIApplication(new FacebookServiceImple()));
	}

}
