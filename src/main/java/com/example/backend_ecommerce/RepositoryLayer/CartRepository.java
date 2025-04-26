package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_ecommerce.Models.Cart;
import com.example.backend_ecommerce.Models.CartDTO;

public interface CartRepository extends JpaRepository<Cart, BigInteger> {

    @Query(value = "SELECT COUNT(*) FROM cart where cart.user_id = :userId", nativeQuery = true)
    public Integer countCartItems(@Param("userId") BigInteger userId);

    @Query(value = "SELECT products.id,products.title,products.image,products.price,cart.count FROM cart inner join products on products.id=cart.product_id where cart.user_id = :userId", nativeQuery = true)
    public List<CartDTO> getCartItems(@Param("userId") BigInteger userId);

    @Query(value = "SELECT * FROM cart where cart.user_id = :userId and cart.product_id = :product_id", nativeQuery = true)
    Cart getCartByProductIdUserId(@Param("userId") BigInteger userId, @Param("product_id") BigInteger product_id);

    // @Query(value = "",nativeQuery = true)
    void decreaseCartCount();
}
