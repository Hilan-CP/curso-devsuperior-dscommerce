package com.cursodevsuperior.dscommerce.dto;

import java.util.ArrayList;
import java.util.List;

import com.cursodevsuperior.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long id;
	
	@NotBlank(message = "campo obrigatório")
	@Size(min = 3, max = 80, message = "deve ter de 3 a 80 caracteres")
	private String name;
	
	@NotBlank
	@Size(min = 10, message = "deve ter pelo menos 10 caracteres")
	private String description;
	
	@NotNull(message = "campo obrigatório")
	@Positive(message = "deve ser maior que zero")
	private Double price;
	
	private String imgUrl;
	
	@NotEmpty(message = "deve ter pelo menos 1 categoria")
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		entity.getCategories().forEach(c -> categories.add(new CategoryDTO(c)));
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
}
