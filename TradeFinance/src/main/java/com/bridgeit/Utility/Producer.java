package com.bridgeit.Utility;

import java.io.IOException;
import javax.mail.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	@Autowired
	RabbitTemplate rabbitTemplate;

	public void sendMessage(String sendMail) throws IOException {
		rabbitTemplate.convertAndSend(sendMail);

	}
}
