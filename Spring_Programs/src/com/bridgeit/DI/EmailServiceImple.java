package com.bridgeit.DI;

public class EmailServiceImple implements MessageService {

	@Override
	public void sendMessage(String message, String receiver) {
		System.out.println("email send to " + receiver + " with message " + message);
	}

}
