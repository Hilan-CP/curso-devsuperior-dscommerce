package com.cursodevsuperior.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.cursodevsuperior.dscommerce.entities.Order;
import com.cursodevsuperior.dscommerce.entities.OrderStatus;

public class OrderDTO {

	private Long id;
	private Instant moment;
	private OrderStatus status;
	private ClientDTO client;
	private PaymentDTO payment;
	private List<OrderItemDTO> items = new ArrayList<>();
	
	public OrderDTO() {
	}

	public OrderDTO(Order entity) {
		id = entity.getId();
		moment = entity.getMoment();
		status = entity.getStatus();
		client = new ClientDTO(entity.getClient());
		payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
		entity.getItems().forEach(i -> items.add(new OrderItemDTO(i)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}
	
	public Double getTotal() {
		return items.stream().mapToDouble(item -> item.getSubtotal()).sum();
	}
}
