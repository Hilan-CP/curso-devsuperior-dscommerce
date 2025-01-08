package com.cursodevsuperior.dscommerce.repositories;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cursodevsuperior.dscommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	
}
