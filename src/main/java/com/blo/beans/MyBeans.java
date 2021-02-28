package com.blo.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.blo.config.EmailConfig;

/*
 * A class customised to contain beans to be used in app
 */

@Component
public class MyBeans {

	@Autowired
	private EmailConfig emailConfig;

	// Mail Sender
	@Bean
	public JavaMailSender getJavaMailSender() {
		// create a mail sender
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(emailConfig.getHost());
		mailSender.setPort(emailConfig.getPort());
		mailSender.setUsername(emailConfig.getUsername());
		mailSender.setPassword(emailConfig.getPassword());

//	    Properties props = mailSender.getJavaMailProperties();
//	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.auth", "true");
//	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");

		return mailSender;
	}
	


}
