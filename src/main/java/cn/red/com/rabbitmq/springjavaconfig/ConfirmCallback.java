package cn.red.com.rabbitmq.springjavaconfig;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback{

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("confirm--:correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);  
	}

}
