package com.cursodevsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursodevsuperior.dscommerce.entities.User;
import com.cursodevsuperior.dscommerce.services.exceptions.ForbiddenAccessException;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public void validateSelfOrAdmin(Long userId) {
		User user = userService.getAuthenticatedUser();
		if(!user.hasRole("ROLE_ADMIN") || !user.getId().equals(userId)) {
			throw new ForbiddenAccessException("Usuário atual não possui permissão para acessar este recurso");
		}
	}
	
	public void validateSelf(Long userId) {
		User user = userService.getAuthenticatedUser();
		if(!user.getId().equals(userId)) {
			throw new ForbiddenAccessException("Usuário atual não possui permissão para acessar este recurso");
		}
	}
}
