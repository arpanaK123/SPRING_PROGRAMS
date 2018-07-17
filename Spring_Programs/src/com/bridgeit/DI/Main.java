package com.bridgeit.DI;

public class Main {

	public static void main(String[] args) {

		String message = "hi";
		String email = "a@gmail.com";
		String phoneNumber = "8797969698";

		MessageServiceInjector msgServInjector = null;
		Consumer consumer = null;

		msgServInjector = new EmailServiceInjector();
		consumer = msgServInjector.getConsumer();
		consumer.processMessage(message, email);

		msgServInjector = new SmsServiceInjector();
		consumer = msgServInjector.getConsumer();
		consumer.processMessage(message, phoneNumber);

		msgServInjector = new EmailServiceInjector();
		consumer = msgServInjector.getConsumer();
		consumer.processMessage(email, phoneNumber);

		msgServInjector = new FacebookServiceInjector();
		consumer = msgServInjector.getConsumer();
		consumer.processMessage(message, email);
	}

}
