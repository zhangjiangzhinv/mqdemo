package cn.red.com.rabbitmq.javaclient;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "activeQueue")
public class PostCardProducer {
	@Autowired
	private RabbitTemplate amqpTemplate;
	
	@RabbitHandler
	public void produce(){
		amqpTemplate.convertAndSend("Hello, world!");
	}
}
