package com.bridgeit.DI;

public class FacebookServiceImple implements MessageService {

	@Override
	public void sendMessage(String message, String receiver) {
		System.out.println("facebook message send to " + receiver + " with message " + message);
	}

}
