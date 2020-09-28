package com.blo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blo.entity.User;
import com.blo.exception.UserNotFound;
import com.blo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder bCryptPasswordEncoder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	public User findUserByUsername(String username) {
		User user =this.userRepository.findByUsername(username);
		if(user==null) {
			LOGGER.warn("USER [username= " + username + "] NOT FOUND");
			throw new UserNotFound();		
		}
		return user;	
	}
	
	
	//deprecated, I handled this functionality in UserProfile Service
//	public User createUser(User user) {
//		 user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//	      //  user.setRoles(new HashSet<>(roleRepository.findAll()));
//		return this.userRepository.save(user); 
//	}
	

	public boolean usernameExists(String username) {	
		return userRepository.existsUserByUsername(username);
	}
	
}
