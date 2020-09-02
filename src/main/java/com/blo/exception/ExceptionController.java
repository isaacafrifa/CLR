package com.blo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blo.model.APIError;




@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler{
	
//	@ExceptionHandler(CustomerNotFound.class)
//	public ResponseEntity<APIError> handleUserNotFoundException(CustomerNotFound ex, WebRequest request) {
//		APIError errorDetails = new APIError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getLocalizedMessage(),
//				request.getDescription(false) + "",LocalDateTime.now());
//		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//	}
	
	
	
	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<APIError> handleUserNotFoundException(UserNotFound ex, WebRequest request) {
		APIError errorDetails = new APIError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getLocalizedMessage(),
				request.getDescription(false) + "",LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UsernameAlreadyExists.class)
	public ResponseEntity<APIError> handleUserAlreadyExistsException(UsernameAlreadyExists ex, WebRequest request) {
		APIError errorDetails = new APIError(HttpStatus.CONFLICT.value(), ex.getMessage(), ex.getLocalizedMessage(),
				request.getDescription(false) + "",LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	
	

	

}
