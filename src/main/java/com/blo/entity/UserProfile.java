package com.blo.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "clr_user_profile")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private long id;
	
	@Column(name="email")
	@NotBlank(message="Email address is mandatory")
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@Column(name="created_on")
	@CreationTimestamp
	private Date createdOn;
	
	//implementing a unidirectional relationship here
	@OneToOne(targetEntity = User.class,cascade = CascadeType.ALL, orphanRemoval = true)
	//Implementing with a Foreign Key in JPA
	 @JoinColumn(name = "login_id", referencedColumnName = "id") //login_id is the foreign key
    private User user;

	@Column(name = "repwd_token")
    private String resetPasswordToken;

	@Column(name = "repwd_token_creation_date",columnDefinition = "TIMESTAMP")
    private LocalDateTime tokenCreationDate;
	
	//Extra Constructor
	public UserProfile(String email) {
		super();
		this.email = email;
	}

	
}
