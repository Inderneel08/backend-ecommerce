package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.Cart;

public interface CartRepository extends JpaRepository<Cart,BigInteger> {
    
}
