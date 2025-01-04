package com.cursodevsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cursodevsuperior.dscommerce.entities.User;
import com.cursodevsuperior.dscommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.searchByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado: " + username);
		}
		return user;
	}

}
