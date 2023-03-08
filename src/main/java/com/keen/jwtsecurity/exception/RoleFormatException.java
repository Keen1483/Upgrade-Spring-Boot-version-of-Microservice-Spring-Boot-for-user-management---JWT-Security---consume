package com.keen.jwtsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleFormatException extends RuntimeException {

	public RoleFormatException(String message) {
		super(message);
	}

}
