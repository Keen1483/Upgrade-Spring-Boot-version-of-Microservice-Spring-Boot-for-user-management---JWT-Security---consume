package com.keen.jwtsecurity.service;

import java.util.Collection;
import java.util.List;

import com.keen.jwtsecurity.domain.Role;
import com.keen.jwtsecurity.domain.User;

public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username, Collection<Role> roles);
	User getUser(String username);
	List<User> getUsers();
	List<Role> getRoles();
	
	User updateUser(Long id, User user);
	void deleteUser(String username);
}
