package com.cursodevsuperior.dscommerce.dto;

import com.cursodevsuperior.dscommerce.entities.Role;

public class RoleDTO {

	private Long id;
	private String authority;
	
	public RoleDTO() {
	}

	public RoleDTO(Role entity) {
		id = entity.getId();
		authority = entity.getAuthority();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
