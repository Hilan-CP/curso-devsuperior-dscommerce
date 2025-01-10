package com.cursodevsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursodevsuperior.dscommerce.dto.UserDTO;
import com.cursodevsuperior.dscommerce.entities.Role;
import com.cursodevsuperior.dscommerce.entities.User;
import com.cursodevsuperior.dscommerce.repositories.UserRepository;
import com.cursodevsuperior.dscommerce.services.exceptions.ForbiddenAccessException;

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
	
	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User user = getAuthenticatedUser();
		return new UserDTO(user);
	}
	
	protected User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
		String username = jwtPrincipal.getClaim("username");
		User user = repository.findByEmail(username).get();
		return user;
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllByName(String name, Pageable pageable) {
		Page<User> result = repository.searchAllByName(name, pageable);
		return result.map(u -> new UserDTO(u));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User result = repository.findById(id).get();
		return new UserDTO(result);
	}

	@Transactional
	public UserDTO insert(UserDTO dto) {
		//qualquer usuário pode se cadastrar, porem terão permissão de cliente
		User entity = new User();
		entity.addRole(new Role(1L, "ROLE_CLIENT"));
		copyDataAndSave(dto, entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		//permitir que um usuário altere apenas seu proprio cadastro
		//um usuário não pode alterar seu role
		User current = getAuthenticatedUser();
		if(!current.getId().equals(id)) {
			throw new ForbiddenAccessException("Usuário atual não possui permissão para acessar este recurso");
		}
		User entity = repository.getReferenceById(id);
		copyDataAndSave(dto, entity);
		return new UserDTO(entity);
	}

	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	private void copyDataAndSave(UserDTO dto, User entity) {
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
	}

	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPhone(dto.getPhone());
		entity.setBirthDate(dto.getBirthDate());
		if(dto.getPassword() != null) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
	}

	
}
