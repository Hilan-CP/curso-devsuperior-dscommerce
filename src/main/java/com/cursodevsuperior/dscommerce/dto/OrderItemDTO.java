package com.cursodevsuperior.dscommerce.dto;

import com.cursodevsuperior.dscommerce.entities.OrderItem;

public class OrderItemDTO {

	private Long productId;
	private String name;
	private Double price;
	private Integer quantity;
	private String imgUrl;
	
	public OrderItemDTO() {
	}

	public OrderItemDTO(OrderItem entity) {
		productId = entity.getProduct().getId();
		name = entity.getProduct().getName();
		price = entity.getPrice();
		quantity = entity.getQuantity();
		imgUrl = entity.getProduct().getImgUrl();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Double getSubtotal() {
		return price * quantity;
	}
}
