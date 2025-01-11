package com.cursodevsuperior.dscommerce.dto;

import com.cursodevsuperior.dscommerce.entities.Category;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

	private Long id;
	
	@NotBlank(message = "campo obrigatório")
	private String name;
	
	public CategoryDTO() {
	}

	public CategoryDTO(Category entity) {
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
