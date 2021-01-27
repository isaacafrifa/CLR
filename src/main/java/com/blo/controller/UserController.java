package com.blo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.entity.User;
import com.blo.exception.UsernameAlreadyExists;
import com.blo.model.ResponseUser;
import com.blo.service.MyUserDetailsService;
import com.blo.service.UserService;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(allowCredentials = "true") //will add Access-Control-Allow-Credentials: true to every server's response header, which is needed for the client to read the cookies
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	private static final String LOGGED_IN_STATUS = "LOGGED_IN_STATUS";
	private static final String LOGGED_IN_USER = "LOGGED_IN_USER";
	private static final String LOGGED_OUT_STATUS = "LOGGED_OUT_STATUS";


	@Autowired
	MyUserDetailsService userDetailsService;

	@GetMapping("/test")
	public String test() {
		return "test successful";
	}
	
	@GetMapping("/login_success")
	public ResponseUser currentUser(Authentication authentication) {
		// using Authentication as the param instead of Principal, so I can customize my output
		LOGGER.info("LOGIN_SUCCESS ENDPOINT HAS BEEN HIT");
		ResponseUser responseUser = new ResponseUser(authentication.getName(),
				authentication.getAuthorities().toString());
		LOGGER.info("USER [" + responseUser + "] LOGGED IN SUCCESSFULLY");
		return responseUser;

	}

	@GetMapping("/logout_success")
	public Map<String, Object> logout_success() {
		LOGGER.info("LOGOUT_SUCCESS ENDPOINT HAS BEEN HIT");
		Map<String, Object> map = new HashMap<>();
		map.put(LOGGED_OUT_STATUS, "LOGGED_OUT");
		return map;
	}
	

	// method to get CSRF token
		@GetMapping("/api/csrf")
		public String getCSRF(HttpServletResponse res) {
			res.setHeader("Set-Cookie", "XSRF-TOKEN = hello; Secure; HttpOnly; SameSite=None; Path=/; ");
 			return "CSRF Protected";
 		}
		
		
	// method to check if user is logged in
	@GetMapping("/authenticated")
	public Map<String, Object> isLoggedIn(Authentication authentication) {
		
		ResponseUser responseUser = new ResponseUser(authentication.getName(),
				authentication.getAuthorities().toString());
		Map<String, Object> loggedInMap = new HashMap<>();
		loggedInMap.put(LOGGED_IN_STATUS, "LOGGED_IN");
		loggedInMap.put(LOGGED_IN_USER, responseUser);
		LOGGER.info("USER [" + responseUser + "] HIT \"../authenticated\" ENDPOINT AND IS AUTHENTICATED");
		return loggedInMap;
	}

	// method to get username
	@GetMapping("/username")
	public String getUsername(Authentication authentication) {
		return authentication.getName();
	}


	
						/*	  Unused or Improved Methods	 */
	
//	@GetMapping("/login-success")
//	public UserDetails currentUser(Authentication authentication) { 	
//		UserDetails userDetails= (UserDetails) authentication.getPrincipal();
//		LOGGER.info("USER ["+userDetails.getUsername()+"] LOGGED IN SUCCESSFULLY");
//		return userDetails;
//		
//	}
	
//	@GetMapping("/logout")
//	public String logout(Authentication authentication) {
//		LOGGER.info("USER [" + authentication.getName() + "] IS ATTEMPTING LOGOUT");
//		return authentication.getName()+" IS ATTEMPTING LOGOUT";
//	}


	
}
