package com.blo.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This is my custom authentication failure handler class
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
	 private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		//attaching request's ip address to log
		LOGGER.warn(request.getRemoteAddr() + " HAS HIT AUTHENTICATION FAILURE HANDLER");
		 
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        Map<String, Object> data = new HashMap<>();
	        data.put(
	  	          "Exception", 
	  	          exception.getMessage());
//	        data.put(
//	          "Timestamp", 
//	          LocalDateTime.now());
	        LOGGER.warn(request.getRemoteAddr()+ " CANNOT AUTHENTICATE [ EXCEPTION : "+exception.getMessage()+" ]");
	      
	        response.getOutputStream()
	          .println(objectMapper.writeValueAsString(data));
	}

}
