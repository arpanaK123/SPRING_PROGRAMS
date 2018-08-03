package com.bridgeit.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgeit.service.UserService;

@Component
public class Consumer {

	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	GenerateTokens generateTokens;
	@Autowired
	UserService userService;

	public void sendMessage(String from, String to,String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setFrom(from);
		mailMessage.setSubject("email verification");
		mailMessage.setText("click link to verify email:-"+message);
		javaMailSender.send(mailMessage);
		System.out.println("email send successfully");
	}

}
