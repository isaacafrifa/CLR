package com.blo.controller;


import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.entity.UserProfile;
import com.blo.exception.EmailAlreadyExists;
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
	
	@GetMapping(value="/profile/{username}")
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
	/*
	 * NB: No need to add the ff column to the expected input above because:
	 * "enabled" column is set to 0 by default if it's not included in input
	 * "role" column has been set to 1 by default in the userProfile Service's createUser method
	 */
	@PostMapping(value = "/registration"
	 , consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@Transactional
	public ResponseEntity<Object> register(@Valid @RequestBody UserProfile userProfile) {
		LOGGER.info("INITIATING REGISTRATION OF USER [DETAILS= " + userProfile + "]");

		// sanitizing inputs
		// String whatever = InputSanitizer.cleanIt(whatever);
		if (userService.usernameExists(userProfile.getUser().getUsername())) {
			LOGGER.warn("USERNAME [" + userProfile.getUser().getUsername() + "] ALREADY EXISTS");
			throw new UsernameAlreadyExists();
		}
		
		if (userProfileService.emailExists(userProfile.getEmail())) {
			LOGGER.warn("EMAIL [" + userProfile.getEmail() + "] ALREADY EXISTS");
			throw new EmailAlreadyExists();
		}
		userProfileService.createUser(userProfile);
		LOGGER.info("USER [DETAILS= " + userProfile + "] REGISTERED SUCCESSFULLY");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	
							/* DELETES */
	
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')") or @PreAuthorize("hasRole('ADMIN')"), both work 
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping(value="/profile/{username}")
	@Transactional
	public String delete(@PathVariable (value = "username") String username) {
		LOGGER.info("INITIATING DELETION OF USER PROFILE [USERNAME= " + username + "]");
		UserProfile foundUserProfile = userProfileService.findUserProfileByUsername(username);
		
		userProfileService.deleteUserProfile(foundUserProfile);
		 LOGGER.info("USER PROFILE  [DETAILS= " + foundUserProfile + "]" + " DELETED SUCCESSFULLY");
		return "Profile with username: ["+foundUserProfile.getUser().getUsername()+"] deleted successfully";
	}
	

	
	
}
