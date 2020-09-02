package com.blo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException{
	/** Default Serial Version UID*/
	private static final long serialVersionUID = 1L;

	//constructor
	public UserNotFound() {
	    super("User not found");
	  }
}