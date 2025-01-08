package com.cursodevsuperior.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursodevsuperior.dscommerce.entities.OrderItem;
import com.cursodevsuperior.dscommerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
