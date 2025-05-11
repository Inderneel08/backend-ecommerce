package com.example.backend_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class BackendEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendEcommerceApplication.class, args);
	}

}
