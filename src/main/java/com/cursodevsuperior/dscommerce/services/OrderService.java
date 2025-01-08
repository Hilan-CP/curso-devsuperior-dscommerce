package com.cursodevsuperior.dscommerce.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursodevsuperior.dscommerce.dto.OrderDTO;
import com.cursodevsuperior.dscommerce.dto.OrderItemDTO;
import com.cursodevsuperior.dscommerce.entities.Order;
import com.cursodevsuperior.dscommerce.entities.OrderItem;
import com.cursodevsuperior.dscommerce.entities.OrderStatus;
import com.cursodevsuperior.dscommerce.entities.Payment;
import com.cursodevsuperior.dscommerce.entities.Product;
import com.cursodevsuperior.dscommerce.entities.User;
import com.cursodevsuperior.dscommerce.repositories.OrderItemRepository;
import com.cursodevsuperior.dscommerce.repositories.OrderRepository;
import com.cursodevsuperior.dscommerce.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository itemRepository;
	
	@Transactional(readOnly = true)
	public Page<OrderDTO> findByDate(String minDate, String maxDate, Pageable pageable) {
		Instant endDate;
		Instant beginDate;
		if(maxDate.equals("")) {
			endDate = LocalDate.now().atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
		}
		else {
			endDate = LocalDate.parse(maxDate).atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
		}
		if(minDate.equals("")) {
			beginDate = LocalDate.ofInstant(endDate, ZoneOffset.UTC).atStartOfDay().toInstant(ZoneOffset.UTC);
		}
		else {
			beginDate = LocalDate.parse(minDate).atStartOfDay().toInstant(ZoneOffset.UTC);
		}
		Page<Order> result = orderRepository.searchOrderByDate(beginDate, endDate, pageable);
		return result.map(o -> new OrderDTO(o));
	}

	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order result = orderRepository.findById(id).get();
		return new OrderDTO(result);
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order entity = new Order();
		entity.setStatus(OrderStatus.WAITING_PAYMENT);
		entity.setMoment(Instant.now());
		//implementar login e usar usuário logado
		User client = new User();
		client.setId(1L);
		entity.setClient(client);
		for(OrderItemDTO i : dto.getItems()) {
			Product product = productRepository.getReferenceById(i.getProductId());
			OrderItem item = new OrderItem(entity, product, i.getQuantity(), product.getPrice());
			entity.getItems().add(item);
		}
		entity = orderRepository.save(entity);
		itemRepository.saveAll(entity.getItems());
		return new OrderDTO(entity);
	}

	@Transactional
	public OrderDTO updatePayment(Long id) {
		Order entity = orderRepository.getReferenceById(id);
		Payment payment = new Payment();
		payment.setMoment(Instant.now());
		payment.setOrder(entity);
		entity.setPayment(payment);
		entity.setStatus(OrderStatus.PAID);
		entity = orderRepository.save(entity);
		return new OrderDTO(entity);
	}
}
