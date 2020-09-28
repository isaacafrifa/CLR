package com.blo.controller;

import java.util.HashMap;
import java.util.Map;

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
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	private static final String LOGGED_IN_STATUS = "LOGGED_IN_STATUS";
	private static final String LOGGED_IN_USER = "LOGGED_IN_USER";
	private static final String LOGGED_OUT_STATUS = "LOGGED_OUT_STATUS";


//	 @Autowired
//	    AuthenticationManager authenticationManager;

	@Autowired
	MyUserDetailsService userDetailsService;

	@GetMapping("/login_success")
	public ResponseUser currentUser(Authentication authentication) {
		// using Authentication as the param instead of Principal, so I can customize my
		// output
		ResponseUser responseUser = new ResponseUser(authentication.getName(),
				authentication.getAuthorities().toString());
		LOGGER.info("USER [" + responseUser + "] LOGGED IN SUCCESSFULLY");
		return responseUser;

	}

	@GetMapping("/logout_success")
	public Map<String, Object> logout_success() {
		Map<String, Object> map = new HashMap<>();
		map.put(LOGGED_OUT_STATUS, "LOGGED_OUT");
		return map;
	}
	

	// method to check if user is logged in
	@GetMapping("/authenticated")
	public Map<String, Object> isLoggedIn(Authentication authentication) {
		ResponseUser responseUser = new ResponseUser(authentication.getName(),
				authentication.getAuthorities().toString());
		Map<String, Object> loggedInMap = new HashMap<>();
		loggedInMap.put(LOGGED_IN_STATUS, "LOGGED_IN");
		loggedInMap.put(LOGGED_IN_USER, responseUser);
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

	
////
////	using spring security's default "/login" endpoint hence below method not needed
////	@PostMapping(value = "/authenticate")
////	public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception 
////	{
////		//@RequestBody  User authenticationRequest
////		//@ModelAttribute for Multipart form instead of @RequestBody for json
////		//@RequestParam to extract query parameters, form parameters, and even files from the request.
////		//@RequestParam(name = "username") String username, @RequestParam String password
////		
////		LOGGER.error("--------->we are in the method");
////		//User authenticationRequest=new User(username, password);
////		authenticate(authenticationRequest.getUsername(), 
////		authenticationRequest.getPassword());
////		
////		UserDetails userPrincipal = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
////		//JwtUserDetails userDetails = new JwtUserDetails();
////		//userDetails.setUsername(authenticationRequest.getUsername());
////		return ResponseEntity.ok(userPrincipal);	
////			
//////		final String token = jwtTokenUtil.generateToken(userDetails);
//////		return ResponseEntity.ok(new JwtResponse(token));
////	}
////	
////	private void authenticate(String username, String password) throws Exception {
////		try {
////			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
////		} catch (DisabledException e) {
////			throw new Exception("USER_DISABLED", e);
////		} catch (BadCredentialsException e) {
////			throw new Exception("INVALID_CREDENTIALS", e);
////		}
//	
//}
	
}
