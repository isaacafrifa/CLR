package com.blo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blo.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	UserProfile findByUserUsername(String username);

	UserProfile findByEmail(String email);

	boolean existsByEmail(String email);
	
	public UserProfile findByResetPasswordToken(String token);
}
