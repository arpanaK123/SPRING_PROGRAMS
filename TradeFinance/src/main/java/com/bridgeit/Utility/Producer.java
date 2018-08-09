package com.bridgeit.Utility;

import java.io.IOException;
import javax.mail.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	@Autowired
	JavaMailSender javaMailSender;
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
