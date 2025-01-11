package com.cursodevsuperior.dscommerce.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.cursodevsuperior.dscommerce.entities.Role;
import com.cursodevsuperior.dscommerce.entities.User;
import com.cursodevsuperior.dscommerce.validation.SignUp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class UserDTO {

	private Long id;
	
	@NotBlank(message = "campo obrigatório")
	private String name;
	
	@NotBlank(message = "campo obrigatório")
	private String email;
	
	@NotBlank(message = "campo obrigatório")
	private String phone;
	
	@NotNull(message = "campo obrigatório")
	@Past(message = "data precisa ser passada")
	private LocalDate birthDate;
	
	@NotBlank(message = "campo obrigatório", groups = SignUp.class)
	private String password;
	
	private List<RoleDTO> roles = new ArrayList<>();
	
	public UserDTO() {
	}

	public UserDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		phone = entity.getPhone();
		birthDate = entity.getBirthDate();
		//não copiar senha - não enviar senha ao controller
		for(GrantedAuthority authority : entity.getAuthorities()) {
			Role role = (Role) authority;
			roles.add(new RoleDTO(role));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}
}
