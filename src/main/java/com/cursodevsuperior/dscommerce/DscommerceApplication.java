package com.cursodevsuperior.dscommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DscommerceApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DscommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Estou executando...");
	}

}
