package com.blo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		//get userProfile's password and encrypt using your bCrpyt encoder
		String encryptedPassword=bCryptPasswordEncoder.encode(userProfile.getUser().getPassword());	
		// overwrite userProfile's password with encrypted password here
		userProfile.getUser().setPassword(encryptedPassword);
		
		// setting userProfile's role to 1 by default
		userProfile.getUser().setRole(1);
	
		return this.userProfileRepository.save(userProfile);

	}

	
	@Transactional
	public void deleteUserProfile(UserProfile userProfile) {
		this.userProfileRepository.delete(userProfile);
		
	}
	
	public boolean emailExists(String email) {	
		return userProfileRepository.existsByEmail(email);
	}
	
	
	
	
}
