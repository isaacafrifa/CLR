package com.blo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import com.blo.model.ResponseUser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if (request == null) {
			LOGGER.info("AUTHENTICATION SUCCESS HANDLER HAS BEEN HIT");
		}
		
		LOGGER.info(request.getRemoteAddr() + " HAS HIT AUTHENTICATION SUCCESS HANDLER");
		ResponseUser responseUser = new ResponseUser(authentication.getName(),
				authentication.getAuthorities().toString());
		LOGGER.info("USER [" + responseUser + "] LOGGED IN SUCCESSFULLY");

		// return responseUser;
		response.getOutputStream().println(objectMapper.writeValueAsString(responseUser));

	}

}
