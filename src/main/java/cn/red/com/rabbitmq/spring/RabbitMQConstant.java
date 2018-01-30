package cn.red.com.rabbitmq.spring;

public class RabbitMQConstant {
	
	// 在该队列中的message可以控制自己的失效时间
	public static final String DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_per_message_ttl_queue";
	
	// 在该队列中的所有message都有统一的失效时间
	public static final String DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay_per_queue_ttl_queue";
	public static final int QUEUE_EXPIRATION = 4000;


	// 实际消费队列，死信会被路由到该队列进行消费
	public static final String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue";
		
	// 死信交换器
	// 该交换器绑定的队列是DELAY_PROCESS_QUEUE_NAME
	public static final String DELAY_EXCHANGE_NAME = "delay_exchange";
	
	// 交换器，用于缓冲队列
	// 该交换器绑定的队列是DELAY_QUEUE_PER_QUEUE_TTL_NAME和DELAY_QUEUE_PER_MESSAGE_TTL_NAME
	public static final String BUFFER_EXCHANGE_NAME = "buffer_exchange";


	
}
