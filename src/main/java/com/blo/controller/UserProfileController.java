package com.blo.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.entity.UserProfile;
import com.blo.exception.UsernameAlreadyExists;
import com.blo.service.UserProfileService;
import com.blo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserProfileController {


	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private UserService userService;
	
	
							/* GETS */
	
	@GetMapping(value="profile/{username}")
	public UserProfile getProfile(@PathVariable (value = "username") String username ){
		LOGGER.info("GETTING USER PROFILE WITH [USERNAME=" + username + "]");
		UserProfile userProfile= userProfileService.findUserProfileByUsername(username);
		return userProfile;
	}
	

	
							/* POSTS */
	
	/*
	 * Expecting input like this:
	 {
	  "email":"****@gmail.com",

    "user": {
    "username":"****",
    "password":"*****"
    		}
	 }
	 */
	@PostMapping(value = "/registration"
	 , consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Object> register(@Valid @RequestBody UserProfile userProfile) {
		LOGGER.info("INITIATING REGISTRATION OF USER [DETAILS= " + userProfile + "]");

		// sanitizing inputs
		// String whatever = InputSanitizer.cleanIt(whatever);
		if (userService.usernameExists(userProfile.getUser().getUsername())) {
			LOGGER.warn("USERNAME [" + userProfile.getUser().getUsername() + "] ALREADY EXISTS");
			throw new UsernameAlreadyExists();
		}
		userProfileService.createUser(userProfile);
		LOGGER.info("USER [DETAILS= " + userProfile + "] REGISTERED SUCCESSFULLY");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
}
