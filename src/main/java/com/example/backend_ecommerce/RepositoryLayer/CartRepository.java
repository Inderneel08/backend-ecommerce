package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_ecommerce.Models.Cart;

public interface CartRepository extends JpaRepository<Cart, BigInteger> {

    @Query(value = "SELECT COUNT(*) FROM cart where cart.user_id = :userId", nativeQuery = true)
    public Integer countCartItems(@Param("userId") BigInteger userId);
}
