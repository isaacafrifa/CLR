/**
 * 
 */
package com.blo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class's object is a customized object that
 *  will be sent to frontend after successful login and authentication
 *
 */

@Data
@NoArgsConstructor
public class ResponseUser {

	private String username;
	private String authority;
	//private Collection<? extends GrantedAuthority> roleList; //using String instead of Collection now
	
	public ResponseUser(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	} 
	
	//commented out so I have grantedAuthority as a String not collection
//	public ResponseUser(String username, Collection<? extends GrantedAuthority>  collection) {
//		super();
//		this.username = username;
//		this.roleList = collection;
//	} 
	
	
}
