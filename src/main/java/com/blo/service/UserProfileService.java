package com.blo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blo.entity.User;
import com.blo.entity.UserProfile;
import com.blo.exception.UserNotFound;
import com.blo.repository.UserProfileRepository;

@Service
public class UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileService.class);

	
	public UserProfile findUserProfileByUsername(String username) {
		UserProfile userProfile =this.userProfileRepository.findByUserUsername(username);
		if(userProfile==null) {
			LOGGER.warn("USER PROFILE WITH [USERNAME= " + username + "] NOT FOUND");
			throw new UserNotFound();		
		}
		return userProfile;	
	}
	
	
	
	@Transactional //hence revert changes if inserting to either tables fails
	public UserProfile createUser(UserProfile userProfile) {
		// create user object and set params with userProfile
		User encryptedUser = new User();
		encryptedUser.setUsername(userProfile.getUser().getUsername());
		// encrypt password here
		encryptedUser.setPassword(bCryptPasswordEncoder.encode(userProfile.getUser().getPassword()));

		// set your user object to overwrite initial user param of userDetails
		userProfile.setUser(encryptedUser);
		return this.userProfileRepository.save(userProfile);

	}


	
}
