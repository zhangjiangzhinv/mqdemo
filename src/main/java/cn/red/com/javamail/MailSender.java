package cn.red.com.javamail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	public static MimeMessage buildTextMessage(Session session,String fromAddress,String toAddress,String subject,String body) throws Exception{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setText(body);
		return message;
	}
	
	public static void sendSMTPMail(String smtpHost,String fromAddress,String password,String toAddress,String subject,String body) throws Exception{
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp"); // ʹ�õ�Э�飨JavaMail�淶Ҫ��
		props.setProperty("mail.smtp.host", smtpHost); // �����˵������ SMTP��������ַ
		props.setProperty("mail.smtp.auth", "true"); // ������֤���������������ʵ���й�
		
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = buildTextMessage(session,fromAddress,toAddress,subject,body);
		message.saveChanges();
		
		Transport transport = session.getTransport("smtp");
		transport.connect(fromAddress,password);
		//message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
		transport.sendMessage(message,message.getAllRecipients());
		transport.close();
		
	}
}
