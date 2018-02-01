package cn.red.com.rabbitmq.springxmlconfig;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import cn.red.com.javamail.MailSender;

@Component
public class MyConsumer implements ChannelAwareMessageListener{
	
	@Override
	public void onMessage(Message message,Channel channel) throws Exception {
		String realMessage = new String(message.getBody());
		String smtpHost = "smtp.126.com";
		String fromAddress = "jianghong8911@126.com";
		String password = "0987poiulkjh";
		String toAddress = "1256149083@qq.com";
		String subject = "≤‚ ‘” º˛";
		String body = new String(message.getBody())+"\n It was sent at "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		MailSender.sendSMTPMail(smtpHost, fromAddress, password, toAddress, subject, body);
		System.out.println("================================Received <" + realMessage + ">");
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
