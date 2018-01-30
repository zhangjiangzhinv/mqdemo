package cn.red.com.rabbitmq.spring;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	/**
	 * �������У��ö����е�ÿ��message���������Լ�������ʱ��
	 * @return
	 */
	@Bean
	Queue delayQueuePerMessageTTL() {
		return QueueBuilder.durable(RabbitMQConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
			.withArgument("x-dead-letter-exchange", RabbitMQConstant.DELAY_EXCHANGE_NAME) // DLX��dead letter���͵���exchange
			.withArgument("x-dead-letter-routing-key", RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME) // dead letterЯ����routing key
			.build();
	}
	
	/**
	 * �������У��ö����е�����message����ͳһ������ʱ��
	 * @return
	 */
	@Bean
	Queue delayQueuePerQueueTTL() {
		return QueueBuilder.durable(RabbitMQConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME)
			.withArgument("x-dead-letter-exchange", RabbitMQConstant.DELAY_EXCHANGE_NAME) // DLX��dead letter���͵���exchange
			.withArgument("x-dead-letter-routing-key", RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME) // dead letterЯ����routing key
			.withArgument("x-message-ttl", RabbitMQConstant.QUEUE_EXPIRATION) // ���ö��еĹ���ʱ��
			.build();
	}
	
	/**
	 * �������У��ö�����ʵ�����Ѷ��С����Żᱻ���Ž�����DLX·�ɵ��ö���
	 * @return
	 */
	@Bean
	Queue delayProcessQueue() {
		return QueueBuilder.durable(RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME)
			.build();
	}



}
