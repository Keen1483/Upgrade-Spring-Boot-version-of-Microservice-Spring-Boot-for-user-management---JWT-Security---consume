package com.keen.jwtsecurity.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keen.jwtsecurity.domain.Role;
import com.keen.jwtsecurity.domain.User;
import com.keen.jwtsecurity.exception.ModelNotFoundException;
import com.keen.jwtsecurity.exception.RoleFormatException;
import com.keen.jwtsecurity.repo.RoleRepo;
import com.keen.jwtsecurity.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepo userRepo;
	private final RoleRepo roleRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			log.info("User found in the database: {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}
	
	@Override
	public User saveUser(User user) {
		log.info("Saving new user {} to the database", user.getUsername());
		String username = user.getUsername() == null ? user.getEmail() : user.getUsername();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		if (role.getName().matches("^((?!ROLE)(?!ROLE_)[A-Z0-9_])+$")) {
			role.setName("ROLE_" + role.getName());
			log.info("Saving new role {} to the database", role.getName());
			return roleRepo.save(role);
		} else {
			throw new RoleFormatException("Role name must containt only the uppercases and the digits");
		}
	}

	@Override
	public void addRoleToUser(String username, Collection<Role> roles) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			roles.removeAll(user.getRoles());
			if (!roles.isEmpty()) {
				for (Role role : roles) {
					if (role != null && roleRepo.findByName(role.getName()) != null) {
						user.getRoles().add(roleRepo.findByName(role.getName()));
						log.info("Adding role {} to user {}", role.getName(), username);
					} else {
						log.error("Role {} not found", role.getName());
					}
				}
				userRepo.save(user);
			} else {
				log.info("User {} already have these role(s)", username);
			}
		} else {
			log.error("Username {} not found", username);
		}
	}

	@Override
	public User getUser(String username) {
		log.info("Fetching user {}", username);
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		log.info("Fetching all users");
		return userRepo.findAll();
	}
	
	@Override
	public List<Role> getRoles() {
		log.info("Fetching all roles");
		return roleRepo.findAll();
	}
	
	@Override
	public User updateUser(Long id, User user) {
		User currentUser = userRepo.findById(id).get();
		
		if (currentUser != null) {
			log.info("Updating user {}", currentUser.getUsername());
			
			String name = user.getName();
			if (name != null) {
				currentUser.setName(name);
			}
			
			String newUsername = user.getUsername();
			if (newUsername != null) {
				currentUser.setUsername(newUsername);
			}
			
			String password = user.getPassword();
			if (password != null) {
				currentUser.setPassword(password);
			}
			
			String email = user.getEmail();
			if (email != null) {
				currentUser.setEmail(email);
			}
			
			Collection<Role> roles = user.getRoles();
			roles.removeAll(currentUser.getRoles());
			if (!roles.isEmpty()) {
				this.addRoleToUser(newUsername, roles);
			}
			
			return userRepo.save(currentUser);
		} else {
			throw new ModelNotFoundException("User not found in database");
		}
	}
	
	@Override
	public void deleteUser(String username) {
		log.error("Deleting user {}", username);
		userRepo.deleteByUsername(username);
	}


}
