package com.bridgeit.Utility;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgeit.service.UserService;

@Component
public class Consumer {
	@Autowired
	RabbitTemplate rabbitTemplate;

	public void sendMessage(UserMail sendMail) throws IOException {
		rabbitTemplate.convertAndSend(sendMail);

	}
}
 