package com.blo.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;

	// creating email instance
		public void sendResetPasswordEmail(String recipientEmail, String userName, String link)
				throws MessagingException, UnsupportedEncodingException {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("support@clr.com", "CLR Support");
			helper.setTo(recipientEmail);

			String subject = "Password Reset Request";
			String content = "<p>Hello <b>" + userName + "</b>,</p>"
					+ "<p>We have received a request for your password to be reset.</p>"
					+ "<p>To reset your password, please click on the link below:</p>" 
					+ "<p><a href=\"" + link
					+ "\">Change my password</a></p>"
					+ "<p>Kindly ignore this email if you remember your password or you did not make this request.</p>"
					+ "<br>" + "<b>- CLR Support Team</b>";

			helper.setSubject(subject);
			helper.setText(content, true);

			// send mail
			mailSender.send(message);
		}
	

}
