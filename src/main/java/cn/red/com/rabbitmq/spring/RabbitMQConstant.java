package cn.red.com.rabbitmq.spring;

public class RabbitMQConstant {
	
	// �ڸö����е�message���Կ����Լ���ʧЧʱ��
	public static final String DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_per_message_ttl_queue";
	
	// �ڸö����е�����message����ͳһ��ʧЧʱ��
	public static final String DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay_per_queue_ttl_queue";
	public static final int QUEUE_EXPIRATION = 4000;


	// ʵ�����Ѷ��У����Żᱻ·�ɵ��ö��н�������
	public static final String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue";
		
	// ���Ž�����
	// �ý������󶨵Ķ�����DELAY_PROCESS_QUEUE_NAME
	public static final String DELAY_EXCHANGE_NAME = "delay_exchange";
	
	// �����������ڻ������
	// �ý������󶨵Ķ�����DELAY_QUEUE_PER_QUEUE_TTL_NAME��DELAY_QUEUE_PER_MESSAGE_TTL_NAME
	public static final String BUFFER_EXCHANGE_NAME = "buffer_exchange";


	
}
