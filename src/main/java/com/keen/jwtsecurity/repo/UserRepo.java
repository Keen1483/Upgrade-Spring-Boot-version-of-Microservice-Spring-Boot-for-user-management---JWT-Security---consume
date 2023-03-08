package com.keen.jwtsecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keen.jwtsecurity.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsername(String username);
	void deleteByUsername(String username);
}
