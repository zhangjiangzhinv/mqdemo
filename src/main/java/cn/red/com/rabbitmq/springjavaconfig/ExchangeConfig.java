package cn.red.com.rabbitmq.springjavaconfig;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://github.com/Lovelcp/blog-demos/blob/master/spring-boot-rabbitmq-delay-queue/src/main/java/com/wooyoo/blog/spring_boot/rabbitmq/delay/queue/QueueConfig.java
 * @author think
 *
 */
@Configuration
public class ExchangeConfig {
	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private ProcessConsumer processConsumer;
	@Autowired
	private ConfirmCallback confirmCallback;
	@Autowired
	private ReturnCallback returnCallback;
	
	/**
	 * 创建死信交换器
	 * @return
	 */
	@Bean
	DirectExchange delayExchange() {
		return new DirectExchange(RabbitMQConstant.DELAY_EXCHANGE_NAME);
	}

	@Bean
	DirectExchange bufferExchange(){
		return new DirectExchange(RabbitMQConstant.BUFFER_EXCHANGE_NAME);
	}
	
	/**
	 * 将实际消费队列绑定到死信交换器
	 * @param delayProcessQueue
	 * @param delayExchange
	 * @return
	 */
	@Bean
	Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
		return BindingBuilder.bind(delayProcessQueue)
			.to(delayExchange)
			.with(RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME);// routingKey
	}
	
    /**
     * 将队列delay_per_queue_ttl_queue绑定到buffer_exchange
     * @param delayQueuePerQueueTTL 该队列中的所有message都有统一的生存时间
     * @param bufferExchange
     * @return
     */
    @Bean
    Binding queueTTLBinding(Queue delayQueuePerQueueTTL, DirectExchange bufferExchange) {
        return BindingBuilder.bind(delayQueuePerQueueTTL)
                             .to(bufferExchange)
                             .with(RabbitMQConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME);
    }
    
    /**
     * 将队列delay_per_message_ttl_queue绑定到buffer_exchange
     * @param delayQueuePerMessageTTL
     * @param bufferExchange
     * @return
     */
    @Bean
    Binding messageTTLBinding(Queue delayQueuePerMessageTTL, DirectExchange bufferExchange) {
        return BindingBuilder.bind(delayQueuePerMessageTTL)
                             .to(bufferExchange)
                             .with(RabbitMQConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME);
    }

	  /**
     * 定义delay_process_queue队列的Listener Container
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    SimpleMessageListenerContainer processContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMQConstant.DELAY_PROCESS_QUEUE_NAME); // 监听delay_process_queue
        container.setMessageListener(new MessageListenerAdapter(processConsumer));
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }
   
 
    @Bean
    RabbitTemplate rabbitTemplate(){
    	RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    	rabbitTemplate.setExchange(RabbitMQConstant.BUFFER_EXCHANGE_NAME);
    	rabbitTemplate.setConfirmCallback(confirmCallback);
    	rabbitTemplate.setReturnCallback(returnCallback);
    	return rabbitTemplate;
    }
}
