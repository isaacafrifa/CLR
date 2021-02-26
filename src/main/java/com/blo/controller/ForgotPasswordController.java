package com.blo.controller;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blo.entity.UserProfile;
import com.blo.model.GenericResponse;
import com.blo.model.OperationsEnum;
import com.blo.model.Utility;
import com.blo.service.UserProfileService;

@RestController
public class ForgotPasswordController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private JavaMailSender mailSender;

	
	@GetMapping("/reset_password/check_token")
	public GenericResponse checkResetPasswordTokenValidity(HttpServletRequest request, @RequestParam(value = "token") String token) {
	   
		LOGGER.info("CHECKING RESET PASSWORD TOKEN VALIDITY [ TOKEN = "+token+" ]");
	    // attaching request's ip address to log
 		LOGGER.info("IP ADDRESS = [" +request.getRemoteAddr() + " ] MADE THIS REQUEST");
 		
	    UserProfile userProfile = userProfileService.getByResetPasswordToken(token);
	    if (userProfile == null) {
	    	 LOGGER.warn("RESET TOKEN = [ "+token+" ] IS INVALID");
			return new GenericResponse(OperationsEnum.UNSUCCESSFUL_OPERATION.toString(),"Invalid Reset Token");
	    }
	    
	    LOGGER.info("RESET TOKEN = [ "+token+" ] IS VALID");
		return new GenericResponse(OperationsEnum.SUCCESSFUL_OPERATION.toString(),null);
	}
	
	
	
	

	
	@PostMapping(value = "/forgot_password")
	public GenericResponse processForgotPassword(HttpServletRequest request, @RequestParam(value = "email") String userEmail) {
		LOGGER.info("FORGOT_PASSWORD'S EMAIL CHECK [ EMAIL ADDRESS = " + userEmail + "]");
		
		UserProfile user = userProfileService.findUserProfileByEmail(userEmail);
		/* commented out because this condition is checked in userProfileService */
		// if (user==null) { LOGGER.warn("USER WITH EMAIL ADDRESS = [ "+userEmail+" ] DOES NOT EXIST" ); throw new UserNotFound();}
		LOGGER.info("USER WITH EMAIL ADDRESS = [ " + userEmail + " ] EXISTS");
		
		// create random token
		String token = UUID.randomUUID().toString();
		// userProfileService.createPasswordResetTokenForUser(user, token);

		try {
		    userProfileService.updateResetPasswordToken(token, userEmail);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;
			LOGGER.info("RESET PASSWORD LINK = [ " + resetPasswordLink + "] CREATED");
			
			sendResetPasswordEmail(userEmail, user.getUser().getUsername(), resetPasswordLink);
			LOGGER.info("RESET PASSWORD EMAIL SENT TO [EMAIL = " + userEmail + ", WITH LINK = " + resetPasswordLink + "]");
		}
		catch (UnsupportedEncodingException | MessagingException e) {
			LOGGER.error("Error while sending email");
		}
		return new GenericResponse(OperationsEnum.SUCCESSFUL_OPERATION.toString(),null);
	}

	
	@PostMapping("/reset_password")
	public GenericResponse processResetPassword( HttpServletRequest request , @RequestParam(value = "token") String token,
			@RequestParam(value = "password") String newPassword) {	
		
		//String pass= request.getParameter("password");
		LOGGER.info("PROCESSING RESET PASSWORD");
		LOGGER.info("CHECKING RESET PASSWORD TOKEN VALIDITY [ TOKEN = "+token+" ]");
		
		//checking token validity
		UserProfile userProfile = userProfileService.getByResetPasswordToken(token);
		if (userProfile == null) {
			LOGGER.warn("RESET TOKEN = [ "+token+" ] IS INVALID");
			return new GenericResponse(OperationsEnum.UNSUCCESSFUL_OPERATION.toString(),"Invalid Reset Token");
		}
		
	    LOGGER.info("RESET TOKEN = [ "+token+" ] IS VALID");
	    //update with new password
		LOGGER.info("RESETTING PASSWORD OF USER = [ "+userProfile.getEmail()+" ]");
	    UserProfile updatedUser =userProfileService.updatePassword(userProfile, newPassword);
	    if(updatedUser == null) {
	    	LOGGER.warn("RESETTING PASSWORD [ USER = "+userProfile.getEmail()+" ] FAILED");
	    	return new GenericResponse(OperationsEnum.UNSUCCESSFUL_OPERATION.toString(),"Password Reset Failed");
	    }
	    
		LOGGER.info("RESETTING PASSWORD OF USER = [ "+userProfile.getEmail()+" ] IS SUCCESSFUL");
		return new GenericResponse(OperationsEnum.SUCCESSFUL_OPERATION.toString(),null);
	}
	
	
	
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
