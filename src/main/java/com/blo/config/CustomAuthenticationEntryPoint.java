package com.blo.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		// attaching request's ip address to log
		LOGGER.warn("IP ADDRESS ["+request.getRemoteAddr() + "] HAS HIT AUTHENTICATION ENTRY POINT");
		
		response.addHeader("WWW-Authenticate", "Realm =  Afrifa");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		LOGGER.warn("IP ADDRESS ["+request.getRemoteAddr() + "] RECEIVED A HTTP STATUS 401 [ EXCEPTION MESSAGE: " + authException.getMessage()
				+ " ]");

		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 - " + authException.getMessage());
	}



	
}
