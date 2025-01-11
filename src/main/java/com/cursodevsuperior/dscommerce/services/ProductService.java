package com.cursodevsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cursodevsuperior.dscommerce.dto.CategoryDTO;
import com.cursodevsuperior.dscommerce.dto.ProductDTO;
import com.cursodevsuperior.dscommerce.dto.ProductMinDTO;
import com.cursodevsuperior.dscommerce.entities.Category;
import com.cursodevsuperior.dscommerce.entities.Product;
import com.cursodevsuperior.dscommerce.repositories.ProductRepository;
import com.cursodevsuperior.dscommerce.services.exceptions.DatabaseException;
import com.cursodevsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAllByName(String name, Pageable pageable) {
		Page<Product> result = repository.searchAllByName(name, pageable);
		return result.map(p -> new ProductMinDTO(p));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product result = repository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new ProductDTO(result);
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDataAndSave(dto, entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
			copyDataAndSave(dto, entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade relacional");
		}
	}
	
	private void copyDataAndSave(ProductDTO dto, Product entity) {
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		for(CategoryDTO c : dto.getCategories()) {
			entity.getCategories().add(new Category(c.getId(), null));
		}
	}
}
