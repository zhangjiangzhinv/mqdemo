package cn.red.com.rabbitmq.javaclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	public void sendNow() throws Exception{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare("java_client_exchange", "direct");
		channel.queueDeclare("java_client_queue", false, false, false, null);
		String message = "This is java_client";
		channel.basicPublish("java_client_exchange", "", null, message.getBytes("UTF-8"));
	}
	public static void main(String args[]){
		
	}
}
