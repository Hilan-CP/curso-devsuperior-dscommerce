package com.cursodevsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cursodevsuperior.dscommerce.dto.UserDTO;
import com.cursodevsuperior.dscommerce.entities.Role;
import com.cursodevsuperior.dscommerce.entities.User;
import com.cursodevsuperior.dscommerce.repositories.UserRepository;
import com.cursodevsuperior.dscommerce.services.exceptions.DatabaseException;
import com.cursodevsuperior.dscommerce.services.exceptions.ForbiddenAccessException;
import com.cursodevsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.searchByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
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
		User result = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
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
		validateSelf(id);
		try {
			User entity = repository.getReferenceById(id);
			copyDataAndSave(dto, entity);
			return new UserDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try{
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade relacional");
		}
	}
	
	private void validateSelf(Long userId) {
		User current = getAuthenticatedUser();
		if(!current.getId().equals(userId)) {
			throw new ForbiddenAccessException("Usuário atual não possui permissão para acessar este recurso");
		}
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
		
		//senha pode ser nula ao atualizar cadastro
		if(dto.getPassword() != null) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
	}
}
