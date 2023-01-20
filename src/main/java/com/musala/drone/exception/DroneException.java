package com.musala.drone.exception;

import org.springframework.http.HttpStatus;

public class DroneException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus httpStatus;

	public DroneException(String message) {
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		this.message = message;
	}

	public DroneException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
}
