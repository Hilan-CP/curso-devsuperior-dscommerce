package com.cursodevsuperior.dscommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cursodevsuperior.dscommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query("SELECT c FROM Category c WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name,'%'))")
	Page<Category> searchAllByName(String name, Pageable pageable);
}
