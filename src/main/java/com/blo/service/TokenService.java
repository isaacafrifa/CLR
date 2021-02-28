package com.blo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.blo.model.Constants;

@Service
public class TokenService {
	/**
	 * Generate unique token. You may add multiple parameters to create a strong
	 * token.
	 * @return unique token
	 */
	public String generateToken() {
		/* commented out cos token is too long */
//		StringBuilder token = new StringBuilder();
//		return token.append(UUID.randomUUID().toString())
//				.append(UUID.randomUUID().toString()).toString();
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Check whether the created token expired or not.
	 * @param tokenCreationDate
	 * @return true or false
	 */
	public boolean isTokenExpired( LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= Constants.EXPIRE_TOKEN_AFTER_MINUTES; //expire_token is 30 minutes
	}
	
	

}
