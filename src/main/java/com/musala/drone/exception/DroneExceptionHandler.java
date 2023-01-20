package com.musala.drone.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class DroneExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle DroneException
	 *
	 * @return the DroneError object
	 */
	@ExceptionHandler(DroneException.class)
	protected ResponseEntity<Object> handleDroneException(DroneException ex) {
		DroneError error = new DroneError(ex.getHttpStatus());
		error.setMessage(ex.getMessage());
		log.error(ex.getMessage());
		return buildResponseEntity(error);
	}

	/**
	 * Handles NotFoundException.
	 *
	 * @param ex the NotFoundException
	 * 
	 * @return the DroneError object
	 */
	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> handleNotFound(NotFoundException ex) {
		DroneError error = new DroneError(NOT_FOUND);
		error.setMessage(ex.getMessage());
		log.error(ex.getMessage());
		return buildResponseEntity(error);
	}

	/**
	 * Handles EntityNotFoundException. Created to encapsulate errors with more
	 * detail than javax.persistence.EntityNotFoundException.
	 *
	 * @param ex the EntityNotFoundException
	 * @return the DroneError object
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		DroneError error = new DroneError(NOT_FOUND);
		error.setMessage(ex.getMessage());
		log.error(ex.getMessage());
		return buildResponseEntity(error);
	}

	/**
	 * Handle Exception
	 *
	 * @return the DroneError object
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex) {
		DroneError error = new DroneError(HttpStatus.INTERNAL_SERVER_ERROR);
		error.setMessage(ex.getMessage());
		log.error(ex.getMessage());
		return buildResponseEntity(error);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		log.error(ex.getMessage());
		return buildResponseEntity(new DroneError(HttpStatus.BAD_REQUEST, error, ex));
	}

	private ResponseEntity<Object> buildResponseEntity(DroneError error) {
		return new ResponseEntity<>(error, error.getStatus());
	}

}
