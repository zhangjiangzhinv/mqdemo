package cn.red.com.controller;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.red.com.domain.PostCard;
import cn.red.com.rabbitmq.javaclient.PostCardProducer;
import cn.red.com.rabbitmq.spring.ExpirationMessagePostProcessor;
import cn.red.com.rabbitmq.spring.ProcessReceiver;
import cn.red.com.rabbitmq.spring.RabbitMQConstant;

@RestController
@RequestMapping(value="/postcard")
public class PostCardController{
	@Autowired
	private PostCardProducer postCardProducer;
	@Autowired
	@Qualifier(value="amqpTemplate")
	private RabbitTemplate rabbitTemplate;
	
	@GetMapping(value="/{id}")
	public PostCard getPostCard(@PathVariable String id){
		PostCard postCard = new PostCard();
		postCard.setFromName("HongHong");
		postCard.setToName("ChaoChao");
		return postCard;
	}
	
	@GetMapping(value="/send/now")
	public String sendPostCard(){
		postCardProducer.produce();
		return "finished";
	}

	@GetMapping(value="/send/delay")
	public String sendPostCard(Double seconds) throws Exception{
		ProcessReceiver.latch = new CountDownLatch(3);
		for(int i = 1; i <= 3; i++){
			long expiration = i * 1000;
			rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
					(Object)("Message From delay_queue_per_message_ttl with expiration "+expiration),
					new ExpirationMessagePostProcessor(expiration));
		}
		ProcessReceiver.latch.await();
		return "finished";
	}
	
}
