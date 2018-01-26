package cn.red.com.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCardProducer {
	@Autowired
	private RabbitTemplate amqpTemplate;
	
	public void produce(){
		amqpTemplate.convertAndSend("Hello, world!");
	}
}
