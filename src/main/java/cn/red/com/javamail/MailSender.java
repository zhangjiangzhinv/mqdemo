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
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
		
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = buildTextMessage(session,fromAddress,toAddress,subject,body);
		message.saveChanges();
		
		Transport transport = session.getTransport("smtp");
		transport.connect(fromAddress,password);
		//message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
		transport.sendMessage(message,message.getAllRecipients());
		transport.close();
		
	}
}
