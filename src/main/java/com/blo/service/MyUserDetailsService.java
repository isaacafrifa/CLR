package com.blo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blo.controller.UserProfileController;
import com.blo.entity.User;
import com.blo.model.UserPrincipal;
import com.blo.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService 
{

	@Autowired
	private UserRepository userRepository;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user= userRepository.findByUsername(username);
		 
		 if(user==null) throw new UsernameNotFoundException("User doesn't exist");
		 
		 //commented out cos I'm checking for this in UserPrincipal class
//		//checking isEnabled here
//		 if (user.getIsAccountEnabled()!=1) {
//		        throw new DisabledException("User is disabled");
//		    }

		 //Implement lastLogin by setting current timestamp here
		// LOGGER.info("In UserDetailsService method: "+user.toString());
		 
		return new UserPrincipal(user);
	}
	

}
