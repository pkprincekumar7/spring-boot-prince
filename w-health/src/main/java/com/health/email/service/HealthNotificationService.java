package com.health.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.health.model.User;

@Service
public class HealthNotificationService {
	
	@Value("${spring.mail.username}")
	private String from;

	private JavaMailSender javaMailSender;
	
	@Autowired
	private HealthNotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNotification(User user) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(from);
		mail.setSubject(user.getSubject());
		mail.setText(user.getMessageBody());
		javaMailSender.send(mail);
	}
}
