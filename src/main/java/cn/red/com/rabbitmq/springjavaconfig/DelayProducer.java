package cn.red.com.rabbitmq.springjavaconfig;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelayProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendDelay(){
		for(int i = 1; i <= 3; i++){
			long expiration = i*1000;
			String readyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
			// routingKey, message, messagePostProcessor
			// 为每个消息指定TTL
			rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
					(Object)("Message From delay_queue_per_message_ttl with expiration "+expiration+"ms.\n It was ready at "+ readyTime),
					new ExpirationMessagePostProcessor(expiration));
		}
	}
}
