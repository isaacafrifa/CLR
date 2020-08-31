package com.blo.model;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.blo.entity.User;

public class UserPrincipal implements UserDetails 
{

	private User user; 
	public UserPrincipal(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
//		List<SimpleGrantedAuthority> authorities= new ArrayList<SimpleGrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));
//		authorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.toString()));
//		return authorities;
		
		//making all principals as Role_Users for now
		return Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));
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

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
