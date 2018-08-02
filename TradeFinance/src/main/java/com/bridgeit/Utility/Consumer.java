package com.bridgeit.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@Autowired
	JavaMailSender javaMailSender;

	public void sendMessage(String to) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("arpanabitp1317@gmail.com");
		mailMessage.setSubject("email verification");
		mailMessage.setText("user email verification");
		javaMailSender.send(mailMessage);
		System.out.println("email send successfully");
	}

}
