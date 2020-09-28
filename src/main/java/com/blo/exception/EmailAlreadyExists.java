package com.blo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExists extends RuntimeException{
	/** Default Serial Version UID*/
	private static final long serialVersionUID = 1L;

	//constructor
	public EmailAlreadyExists() {
	    super("Email already exists.");
	  }
}