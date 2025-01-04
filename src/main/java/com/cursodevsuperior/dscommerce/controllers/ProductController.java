package com.cursodevsuperior.dscommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursodevsuperior.dscommerce.dto.ProductMinDTO;
import com.cursodevsuperior.dscommerce.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<Page<ProductMinDTO>> findAllByName(@RequestParam(defaultValue = "") String name, Pageable pageable){
		Page<ProductMinDTO> list = service.findAllByName(name, pageable);
		return ResponseEntity.ok(list);
	}
}
