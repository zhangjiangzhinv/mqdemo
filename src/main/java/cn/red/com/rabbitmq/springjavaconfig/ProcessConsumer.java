package cn.red.com.rabbitmq.springjavaconfig;

import com.rabbitmq.client.Channel;

import cn.red.com.javamail.MailSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Component
public class ProcessConsumer implements ChannelAwareMessageListener {
	
    public static CountDownLatch latch;
    
    private static Logger logger = LoggerFactory.getLogger(ProcessConsumer.class);

    public static final String FAIL_MESSAGE = "This message will fail";

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            processMessage(message);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);  
        }
        catch (Exception e) {
            // ����������쳣���򽫸���Ϣ�ض��򵽻�����У�����һ���ӳ�֮���Զ�����
        	// �ô�ʹ�õ���AMQP BASIC.PUBLISH �������߷�����Ϣ
            channel.basicPublish(RabbitMQConstant.BUFFER_EXCHANGE_NAME, RabbitMQConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME, null,
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
        String smtpHost = "smtp.126.com";
        String fromAddress = "jianghong8911@126.com";
        String password = "0987poiulkjh";
        String toAddress = "1256149083@qq.com";
        String subject = "�����ʼ�";
        String body = new String(message.getBody())+"\n It was sent at "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        MailSender.sendSMTPMail(smtpHost, fromAddress, password, toAddress, subject, body);
        logger.info("Received <" + realMessage + ">");
        System.out.println("================================Received <" + realMessage + ">");
        if (Objects.equals(realMessage, FAIL_MESSAGE)) {
            throw new Exception("Some exception happened");
        }
    }
}
