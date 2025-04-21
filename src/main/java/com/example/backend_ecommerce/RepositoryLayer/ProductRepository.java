package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_ecommerce.Models.Products;

public interface ProductRepository extends JpaRepository<Products, BigInteger> {

    @Query(value = "SELECT * FROM products where products.category = :id", nativeQuery = true)
    List<Products> findByCategory(@Param("id") Integer id);

}
