package com.blo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.blo.model.RolesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "clr_login")
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
	private String password;
	
	/* not doing a bidirectional one-to-one mapping, so this LOC is commented out */
//	 @OneToOne(mappedBy = "user")  
//	private UserProfile userProfile;

	
	@Column(name="ENABLED")
	private int isAccountEnabled;

	@Column(name="UPDATED_ON")
	@UpdateTimestamp
	private Date updatedOn;
	

	@Column(name = "ROLE")
	private int role; 
	
	// not using Enum approach 
//	@Column(name = "ROLE")
//	@Enumerated(EnumType.ORDINAL) //using ordinal will pose a problem when order of Enum changes
//	 private RolesEnum role;
	



	//Extra constructor
		public User( String username,String password) {
			super();
			this.username = username;
			this.password = password;
		}
	
}
