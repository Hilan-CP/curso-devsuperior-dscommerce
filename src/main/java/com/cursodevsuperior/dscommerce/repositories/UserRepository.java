package com.cursodevsuperior.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cursodevsuperior.dscommerce.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
	User searchByEmail(String email);
}
