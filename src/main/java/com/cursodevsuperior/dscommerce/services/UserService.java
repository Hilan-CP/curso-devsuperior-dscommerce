package com.cursodevsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursodevsuperior.dscommerce.dto.RoleDTO;
import com.cursodevsuperior.dscommerce.dto.UserDTO;
import com.cursodevsuperior.dscommerce.dto.UserMinDTO;
import com.cursodevsuperior.dscommerce.entities.Role;
import com.cursodevsuperior.dscommerce.entities.User;
import com.cursodevsuperior.dscommerce.repositories.RoleRepository;
import com.cursodevsuperior.dscommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.searchByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado: " + username);
		}
		return user;
	}

	@Transactional(readOnly = true)
	public Page<UserMinDTO> findAllByName(String name, Pageable pageable) {
		Page<User> result = repository.searchAllByName(name, pageable);
		return result.map(u -> new UserMinDTO(u));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User result = repository.findById(id).get();
		return new UserDTO(result);
	}

	@Transactional
	public UserDTO insert(UserDTO dto) {
		//pendências de implementação
		//permitir que somente administadores possam atribuir permissões
		//qualquer usuário pode se cadastrar, porem terão permissão de cliente
		//criptografar senha
		User entity = new User();
		copyDataAndSave(dto, entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		//pendências de implementação
		//permitir que um usuário altere apenas seu proprio cadastro
		//criptografar senha
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
		entity.setPassword(dto.getPassword());
		for(RoleDTO r : dto.getRoles()) {
			//Role possui equals e hashCode comparado por authority, portanto authority não pode ser nulo
			Role role = roleRepository.getReferenceById(r.getId());
			entity.addRole(role);
		}
	}
}
