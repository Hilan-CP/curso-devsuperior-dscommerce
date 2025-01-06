package com.cursodevsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursodevsuperior.dscommerce.dto.CategoryDTO;
import com.cursodevsuperior.dscommerce.entities.Category;
import com.cursodevsuperior.dscommerce.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllByName(String name, Pageable pageable) {
		Page<Category> result = repository.searchAllByName(name, pageable);
		return result.map(c -> new CategoryDTO(c));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Category result = repository.findById(id).get();
		return new CategoryDTO(result);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		copyDataAndSave(dto, entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		Category entity = repository.getReferenceById(id);
		copyDataAndSave(dto, entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	private void copyDataAndSave(CategoryDTO dto, Category entity) {
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
	}

	private void copyDtoToEntity(CategoryDTO dto, Category entity) {
		entity.setName(dto.getName());
	}
}
