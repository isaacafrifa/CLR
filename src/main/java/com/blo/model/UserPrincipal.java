package com.blo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.blo.entity.User;

public class UserPrincipal implements UserDetails {

	private User user;

	public UserPrincipal(User user) {
		this.user = user;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPrincipal.class);
	

	// not using getEnum approach hence this method
	// customized method to convert user's role from int to String
	private String getUserRole(int userRole) {
		if (userRole == 2) {
			return RolesEnum.ROLE_ADMIN.name();
		}
		if (userRole == 3) {
			return RolesEnum.ROLE_SYS_ADMIN.name();
		}
		return RolesEnum.ROLE_USER.name();
	}

	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

//		List<SimpleGrantedAuthority> authorities= new ArrayList<SimpleGrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		return authorities;

		// making all principals as Role_Users for now
		// return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

		String userRole = getUserRole(user.getRole());

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userRole));
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * Spring Security will automatically give
	 * org.springframework.security.authentication.DisabledException: User is
	 * disabled exception based on isEnabled() result.
	 */
	@Override
	public boolean isEnabled() {
		if (user.getIsAccountEnabled() == 1) {
			return true;
		}
		LOGGER.warn("USER[ "+user+" ] TRIED TO LOGIN BUT IS DISABLED");
		return false;
	}

}
