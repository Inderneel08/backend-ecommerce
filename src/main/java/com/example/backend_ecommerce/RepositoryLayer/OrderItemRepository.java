package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.OrderItems;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItems, BigInteger> {

    @Query(value = "SELECT * FROM order_items where order_items.order_id = :order_id",nativeQuery = true)
    public List<OrderItems> getOrderItemsByOrderId(@Param("order_id") BigInteger order_id);
}
