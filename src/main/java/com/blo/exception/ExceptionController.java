package com.blo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blo.model.APIError;




@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler{
		
	
	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<APIError> handleUserNotFoundException(UserNotFound ex, WebRequest request) {
		APIError errorDetails = new APIError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getLocalizedMessage(),
				request.getDescription(false) + "",LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UsernameAlreadyExists.class)
	public ResponseEntity<APIError> handleUsernameAlreadyExistsException(UsernameAlreadyExists ex, WebRequest request) {
		APIError errorDetails = new APIError(HttpStatus.CONFLICT.value(), ex.getMessage(), ex.getLocalizedMessage(),
				request.getDescription(false) + "",LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(EmailAlreadyExists.class)
	public ResponseEntity<APIError> handleEmailAlreadyExistsException(EmailAlreadyExists ex, WebRequest request) {
		APIError errorDetails = new APIError(HttpStatus.CONFLICT.value(), ex.getMessage(), ex.getLocalizedMessage(),
				request.getDescription(false) + "",LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	
	 /*
	  * Handle AccessDeniedException ie. 403 Forbidden
	  */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<APIError> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		  String error = "Forbidden";
		APIError errorDetails = new APIError(HttpStatus.FORBIDDEN.value(),
				ex.getLocalizedMessage(),error,
				request.getDescription(false) + "",LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}
	
	
	 /*
	  fall-back handler – catch all type of logic that deals with all other exceptions that don't have specific handlers
	  	 */
	  	@ExceptionHandler({ Exception.class })
	  	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
	  	    APIError apiError = new APIError(
	  	      HttpStatus.INTERNAL_SERVER_ERROR.value(),  ex.getClass().getSimpleName(),
	  	      "Internal Server Error Occurred",
	  	    request.getDescription(false) + "", LocalDateTime.now());
	  	    return new ResponseEntity<Object>(
	  	    		apiError,HttpStatus.INTERNAL_SERVER_ERROR);
	  	}
	

}
