package com.keen.jwtsecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keen.jwtsecurity.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
