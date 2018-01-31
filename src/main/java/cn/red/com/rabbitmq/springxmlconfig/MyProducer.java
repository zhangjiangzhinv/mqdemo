package cn.red.com.rabbitmq.springxmlconfig;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component 
public class MyProducer {
	@Autowired
	private RabbitTemplate myTemplate;

	public void sendNow(){
		myTemplate.convertAndSend("postcard","Hello, world! \n It is ready at "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
	}
}
