package com.itheima.store.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	public static void sendMail(String to, String code) {
		Properties props = new Properties();
		props.setProperty("smtp", "localhost");
		// 获得连接
		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("ghost@store.com", "111");
			}
		});
		// 构建邮件
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("ghost@store.com"));
			// 设置收件人
			// to收件人,cc抄送,bcc暗送,密送
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// 主题
			message.setSubject("来自黑马商城的一封激活邮件");
			// 正文
			message.setContent(
					"<h1>来自商城的一封激活邮件:请点击连接激活</h1><h3><a href='http://localhost:8080/store_v2.0/UserServlet?method=active&code="
							+ code + "'>"+"激活"+"</a></h3>",
					"text/html;charset=utf-8");
			// 发送邮件   
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		MailUtils.sendMail("Nancy@store.com", "9991222233334444");
	}
}
