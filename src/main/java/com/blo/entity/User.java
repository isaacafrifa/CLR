package com.blo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "bugtracker_login")
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="ID")
	//@JsonIgnore
	private long id;
	
	@Column(name="USERNAME", nullable = false)
	@NotBlank(message="Username must not be empty")
   // @Size(min=6, max=20, message = "Username must be between 6 and 20 characters long")
	private String username;
	
	@Column(name="PASSWORD")
	@NotBlank(message="Password must not be empty")
	 @Size(min=8, message = "Password must be more than 8 characters")
   // @Size(min=10, max=20, message = "Password must be between 10 and 20 characters long")
	private String password;
	
	/* not doing a bidirectional one-to-one mapping, so this LOC is commented out */
//	 @OneToOne(mappedBy = "user")  
//	private UserProfile userProfile;

	
	
	//use enums for the params below
//	private Boolean isAccountEnabled;
//	
//	private String roles; 


	
	
//	
//	@Column(name="LAST_LOGIN")
//	@UpdateTimestamp
//	private Date lastLogin;
	
	//Extra constructor
	public User( String username,String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
}
