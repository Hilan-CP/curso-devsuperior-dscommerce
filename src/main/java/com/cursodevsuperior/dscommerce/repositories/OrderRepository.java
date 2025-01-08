package com.cursodevsuperior.dscommerce.repositories;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cursodevsuperior.dscommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query(value = "SELECT order FROM Order order "
			+ "JOIN FETCH order.items item "
			+ "JOIN FETCH item.id.product "
			+ "LEFT JOIN FETCH order.payment "
			+ "JOIN FETCH order.client "
			+ "WHERE order.moment BETWEEN :minDate AND :maxDate",
			countQuery = "SELECT order FROM Order order "
					+ "JOIN order.items item "
					+ "JOIN item.id.product "
					+ "LEFT JOIN order.payment "
					+ "JOIN order.client "
					+ "WHERE order.moment BETWEEN :minDate AND :maxDate")
	Page<Order> searchOrderByDate(Instant minDate, Instant maxDate, Pageable pageable);
}
