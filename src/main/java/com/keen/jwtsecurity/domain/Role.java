package com.keen.jwtsecurity.domain;

import static jakarta.persistence.GenerationType.AUTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @JsonIgnoreProperties(value = {"id"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Comparable<Role> {
	@Id @GeneratedValue(strategy=AUTO)
	private Long id;
	private String name;
	
	@Override
	public int compareTo(Role role) {
		return this.getName().compareTo(role.getName());
	}
}
