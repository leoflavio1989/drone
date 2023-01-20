package com.musala.drone.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DroneException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(HttpStatus status, String message) {
		super(status, message);
	}

	public NotFoundException(String message) {
		super(message);
	}
}