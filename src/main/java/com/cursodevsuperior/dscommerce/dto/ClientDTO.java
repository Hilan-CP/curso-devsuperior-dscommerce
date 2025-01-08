package com.cursodevsuperior.dscommerce.dto;

import com.cursodevsuperior.dscommerce.entities.User;

public class ClientDTO {

	private Long id;
	private String name;
	
	public ClientDTO() {
	}

	public ClientDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
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
}
