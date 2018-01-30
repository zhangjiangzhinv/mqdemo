package cn.red.com.rabbitmq.spring;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	/**
	 * 创建队列，该队列中的每个message都可以有自己的生存时间
	 * @return
	 */
	@Bean
	Queue delayQueuePerMessageTTL() {
		return QueueBuilder.durable(RabbitMQConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
			.withArgument("x-dead-letter-exchange", RabbitMQConstant.DELAY_EXCHANGE_NAME) // DLX，dead letter发送到的exchange
			.withArgument("x-dead-letter-routing-key", RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key
			.build();
	}
	
	/**
	 * 创建队列，该队列中的所有message都有统一的生存时间
	 * @return
	 */
	@Bean
	Queue delayQueuePerQueueTTL() {
		return QueueBuilder.durable(RabbitMQConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME)
			.withArgument("x-dead-letter-exchange", RabbitMQConstant.DELAY_EXCHANGE_NAME) // DLX，dead letter发送到的exchange
			.withArgument("x-dead-letter-routing-key", RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key
			.withArgument("x-message-ttl", RabbitMQConstant.QUEUE_EXPIRATION) // 设置队列的过期时间
			.build();
	}
	
	/**
	 * 创建队列，该队列是实际消费队列。死信会被死信交换器DLX路由到该队列
	 * @return
	 */
	@Bean
	Queue delayProcessQueue() {
		return QueueBuilder.durable(RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME)
			.build();
	}



}
