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
 * �ӳ�����ģ�͵Ĳ���
 * �ӳ�����ģ�ͣ������߽���Ϣ���͵�������У�����Ϣ������ź󣬻ᱻ���͵�������������õ�DLX�����Ž����������������ŵ�ʵ�����Ѷ��С�
 * ʵ�ֵĹ����ǣ���Ϣ���������ӳ�һ��ʱ���ٱ�ʵ�����ѡ�
 * �ؼ��ʣ�������У���Ϣ��TTL�����е�TTL����Ϣ������ʱ�䣬���Ž�����DLX��ʵ�����Ѷ���
 * @author think
 *
 */
public class DelayConsumeApplicationTest {
	@Autowired
	@Qualifier(value="rabbitTemplate")
	private RabbitTemplate rabbitTemplate;// ��spring-rabbitmq.xml�н���������
	
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
