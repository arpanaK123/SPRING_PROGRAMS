package com.bridgeit.First;

public class MyApplication {
	private EmailService email = new EmailService();

	public void processEmail(String message, String receiver) {
		this.email.emailService(message, receiver);
	}
}
