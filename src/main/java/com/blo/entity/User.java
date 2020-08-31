package com.blo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "bugtracker_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="ID")
	private long id;
	
	@Column(name="USERNAME", nullable = false)
	@NotBlank(message="Username must not be empty")
   // @Size(min=6, max=20, message = "Username must be between 6 and 20 characters long")
	private String username;
	
	@Column(name="PASSWORD")
	@NotBlank(message="Password must not be empty")
	 @Size(min=8, message = "Password must be more than 8 characters")
   // @Size(min=10, max=20, message = "Password must be between 10 and 20 characters long")
	//@JsonIgnore
	private String password;
	
	
	
	//use enums for the params below
//	private Boolean isAccountEnabled;
//	
//	private String roles; 


	
	
//	@Column(name="USER_TYPE")
//	@Min(value = 1, message = "User Type cant be less than 1")
//	@Max(value = 2, message = "User Type cant be more than 2")
//	@NotNull(message = "User type is mandatory")
//	private int userType;
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
