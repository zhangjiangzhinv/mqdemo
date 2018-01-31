package cn.red.com.rabbitmq.springjavaconfig;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * ���ö�����Ϣ�Ĺ���ʱ��
 */
public class ExpirationMessagePostProcessor implements MessagePostProcessor {
	private final Long ttl; // ����

	public ExpirationMessagePostProcessor(Long ttl){
		this.ttl = ttl;
	}

	@Override
	public Message postProcessMessage(Message message)  throws AmqpException {
		// ����per-message��ʧЧʱ��
		message.getMessageProperties().setExpiration(ttl.toString());
		return message;
	}

}