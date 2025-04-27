package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.OrderItems;

public interface OrderItemRepository extends JpaRepository<OrderItems, BigInteger> {
}
