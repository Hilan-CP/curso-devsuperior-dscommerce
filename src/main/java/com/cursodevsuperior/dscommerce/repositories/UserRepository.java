package com.cursodevsuperior.dscommerce.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cursodevsuperior.dscommerce.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
	User searchByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE UPPER(u.name) LIKE UPPER(CONCAT('%', :name,'%'))")
	Page<User> searchAllByName(String name, Pageable pageable);

	Optional<User> findByEmail(String username);
}
