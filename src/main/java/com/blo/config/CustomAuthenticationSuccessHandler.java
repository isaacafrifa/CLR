package com.blo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.blo.model.ResponseUser;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This is my custom authentication success handler class
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		// attaching request's ip address to log
		LOGGER.info("IP ADDRESS [ "+request.getRemoteAddr() + " ] HAS HIT AUTHENTICATION SUCCESS HANDLER");
		ResponseUser responseUser = new ResponseUser(authentication.getName(),
				authentication.getAuthorities().toString());
		LOGGER.info("USER [" + responseUser + "] LOGGED IN SUCCESSFULLY WITH IP ADDRESS [ "+request.getRemoteAddr() +" ]");

		response.getOutputStream().println(objectMapper.writeValueAsString(responseUser));

	}

}
