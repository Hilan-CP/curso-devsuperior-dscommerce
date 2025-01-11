package com.cursodevsuperior.dscommerce.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cursodevsuperior.dscommerce.dto.CustomError;
import com.cursodevsuperior.dscommerce.dto.ValidationError;
import com.cursodevsuperior.dscommerce.services.exceptions.DatabaseException;
import com.cursodevsuperior.dscommerce.services.exceptions.ForbiddenAccessException;
import com.cursodevsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ForbiddenAccessException.class)
	public ResponseEntity<CustomError> forbiddenAccess(ForbiddenAccessException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.FORBIDDEN;
		CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError error = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());
		for(FieldError field : e.getBindingResult().getFieldErrors()) {
			error.addError(field.getField(), field.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(error);
	}
}
