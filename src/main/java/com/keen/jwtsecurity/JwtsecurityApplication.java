package com.keen.jwtsecurity;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.github.javafaker.Faker;
import com.keen.jwtsecurity.domain.Role;
import com.keen.jwtsecurity.domain.User;
import com.keen.jwtsecurity.repo.RoleRepo;
import com.keen.jwtsecurity.service.UserService;

@SpringBootApplication
public class JwtsecurityApplication {
	
	@Autowired
	RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(JwtsecurityApplication.class, args);
	}
	
	/*
	@Bean
	CommandLineRunner run(UserService userService) {
		String[] roleNames = {"USER", "DEV_ASSISTANT", "MANAGER", "ADMIN", "SUPER_ADMIN"};
		return arg -> {
			
			for (String roleName : roleNames) {
				userService.saveRole(new Role(null, roleName));
			}
			
			Faker faker = new Faker();
			for (int i = 0; i < 100; i++) {
				Role role = roleRepo.findByName("ROLE_" + roleNames[(int) (Math.random() * roleNames.length)]);
				userService.saveUser(
						new User(
								null,
								faker.name().fullName(),
								faker.internet().emailAddress(),
								faker.name().username(),
								"1234",
								Arrays.asList(role)
						)
				);
			}
		};
	}*/

}
