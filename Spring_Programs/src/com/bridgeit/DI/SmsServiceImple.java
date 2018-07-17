package com.bridgeit.DI;

public class SmsServiceImple implements MessageService {

	@Override
	public void sendMessage(String message, String receiver) {
		System.out.println("sms send to " + receiver + " with message " + message);
	}

}
