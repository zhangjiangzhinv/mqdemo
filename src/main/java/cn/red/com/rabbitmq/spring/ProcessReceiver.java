package cn.red.com.rabbitmq.spring;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Component
public class ProcessReceiver implements ChannelAwareMessageListener {
	
    public static CountDownLatch latch;
    
    private static Logger logger = LoggerFactory.getLogger(ProcessReceiver.class);

    public static final String FAIL_MESSAGE = "This message will fail";

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            processMessage(message);
        }
        catch (Exception e) {
            // ����������쳣���򽫸���Ϣ�ض��򵽻�����У�����һ���ӳ�֮���Զ�����
        	// �ô�ʹ�õ���AMQP BASIC.PUBLISH �������߷�����Ϣ
            channel.basicPublish(RabbitMQConstant.PER_QUEUE_TTL_EXCHANGE_NAME, RabbitMQConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME, null,
                    "The failed message will auto retry after a certain delay".getBytes());
        }

        if (latch != null) {
            latch.countDown();
        }
    }

    /**
     * ģ����Ϣ�����������Ϣ����ΪFAIL_MESSAGE�Ļ�������Ҫ�׳��쳣
     *
     * @param message
     * @throws Exception
     */
    public void processMessage(Message message) throws Exception {
        String realMessage = new String(message.getBody());
        logger.info("Received <" + realMessage + ">");
        if (Objects.equals(realMessage, FAIL_MESSAGE)) {
            throw new Exception("Some exception happened");
        }
    }
}
