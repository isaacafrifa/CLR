package com.blo.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This is my custom authentication failure handler class
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	 private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		 response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        Map<String, Object> data = new HashMap<>();
	        data.put(
	  	          "Exception", 
	  	          exception.getMessage());
//	        data.put(
//	          "Timestamp", 
//	          LocalDateTime.now());
	      
	        response.getOutputStream()
	          .println(objectMapper.writeValueAsString(data));
	}

}
