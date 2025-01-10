package com.cursodevsuperior.dscommerce.services.exceptions;

public class ForbiddenAccessException extends RuntimeException{

	public ForbiddenAccessException(String message) {
		super(message);
	}
}
