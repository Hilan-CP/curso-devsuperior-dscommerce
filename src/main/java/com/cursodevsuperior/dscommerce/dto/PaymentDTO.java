package com.cursodevsuperior.dscommerce.dto;

import java.time.Instant;

import com.cursodevsuperior.dscommerce.entities.Payment;

public class PaymentDTO {

	private Long id;
	private Instant moment;
	
	public PaymentDTO() {
	}

	public PaymentDTO(Payment entity) {
		id = entity.getId();
		moment = entity.getMoment();
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
}
