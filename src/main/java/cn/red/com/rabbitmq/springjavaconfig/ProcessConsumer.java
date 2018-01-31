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
            // 如果发生了异常，则将该消息重定向到缓冲队列，会在一定延迟之后自动重做
        	// 该处使用的是AMQP BASIC.PUBLISH 是生产者发送消息
            channel.basicPublish(RabbitMQConstant.BUFFER_EXCHANGE_NAME, RabbitMQConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME, null,
                    "The failed message will auto retry after a certain delay".getBytes());
        }

        if (latch != null) {
            latch.countDown();
        }
    }

    /**
     * 模拟消息处理。如果当消息内容为FAIL_MESSAGE的话，则需要抛出异常
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
        String subject = "测试邮件";
        String body = new String(message.getBody())+"\n It was sent at "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        MailSender.sendSMTPMail(smtpHost, fromAddress, password, toAddress, subject, body);
        logger.info("Received <" + realMessage + ">");
        System.out.println("================================Received <" + realMessage + ">");
        if (Objects.equals(realMessage, FAIL_MESSAGE)) {
            throw new Exception("Some exception happened");
        }
    }
}
