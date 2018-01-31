package cn.red.com.rabbitmq.spring;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.red.com.rabbitmq.springjavaconfig.ExpirationMessagePostProcessor;
import cn.red.com.rabbitmq.springjavaconfig.ProcessConsumer;
import cn.red.com.rabbitmq.springjavaconfig.RabbitMQConstant;

/**
 * 延迟消费模型的测试
 * 延迟消费模型：生产者将消息发送到缓冲队列，当消息变成死信后，会被发送到缓冲队列上设置的DLX（死信交换器），进而被放到实际消费队列。
 * 实现的功能是：消息被创建后，延迟一定时间再被实际消费。
 * 关键词：缓冲队列，消息的TTL，队列的TTL，消息的生存时间，死信交换器DLX，实际消费队列
 * @author think
 *
 */
public class DelayConsumeApplicationTest {
	@Autowired
	@Qualifier(value="rabbitTemplate")
	private RabbitTemplate rabbitTemplate;// 在spring-rabbitmq.xml中进行了配置
	
	@Test
	public void testDelayQueuePerMessageTTL() throws Exception{
		ProcessConsumer.latch = new CountDownLatch(3);
		for(int i = 1; i <= 3; i++){
			long expiration = i * 1000;
			rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
					(Object)("Message From delay_queue_per_message_ttl with expiration "+expiration),
					new ExpirationMessagePostProcessor(expiration));
		}
		ProcessConsumer.latch.await();
	}
	
	@Test
	public void testDelayQueuePerQueueTTL() throws Exception{
		ProcessConsumer.latch = new CountDownLatch(3);
		for(int i = 1; i <= 3; i++){
			rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME, 
					(Object)("Message From delay_per_queue_ttl_queue with expiration "+RabbitMQConstant.QUEUE_EXPIRATION));
		}
		ProcessConsumer.latch.await();
	}
	
	@Test
	public void testFailedMessage() throws Exception{
      ProcessConsumer.latch = new CountDownLatch(6);
        for (int i = 1; i <= 3; i++) {
        	rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME, ProcessConsumer.FAIL_MESSAGE);
        }
        ProcessConsumer.latch.await();
	}
}
