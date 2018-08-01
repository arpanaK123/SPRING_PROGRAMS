package com.bridgeit.Utility;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;



public class Application {
	
    static final String topicExchangeName = "spring-boot-exchange";
    static final String queueName = "queue";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
    
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }
    
    
    
}
