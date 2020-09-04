package com.blo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blo.entity.User;
import com.blo.model.UserPrincipal;
import com.blo.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService 
{

	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user= userRepository.findByUsername(username);
		 
		 if(user==null) throw new UsernameNotFoundException("User doesn't exist");
		 
		 //no need,check has been done in UserPrincipal class
//		//checking isEnabled here
//		 if(user.getIsAccountEnabled()!=1) throw new UsernameNotFoundException("User doesn't exist");	 
		return new UserPrincipal(user);
	}

}
