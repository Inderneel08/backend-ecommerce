package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;
import java.util.List;

import com.example.backend_ecommerce.Models.ProductInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_ecommerce.Models.Products;

public interface ProductRepository extends JpaRepository<Products, BigInteger> {

    @Query(value = "SELECT * FROM products where products.category = :id", nativeQuery = true)
    List<Products> findByCategory(@Param("id") Integer id);

    @Query(value = "SELECT products.id as product_id,products.title,products.price,products.description,products.image,rating.rate,category.id as category_id,category.category_name FROM products inner join rating on products.rate_id=rating.id inner join category on products.category=category.id where products.id = :id",nativeQuery = true)
    List<ProductInfoDTO> getProductInfo(@Param("id") BigInteger id);

}
