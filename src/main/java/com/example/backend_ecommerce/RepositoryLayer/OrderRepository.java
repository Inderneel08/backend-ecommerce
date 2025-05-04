package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.Orders;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders,BigInteger>{

    @Query(value = "SELECT * FROM orders where orders.current_status = :current_status",nativeQuery = true)
    public List<Orders> fetchOrdersByStatusAndTableId(@Param("current_status") Integer current_status);

    @Modifying
    @Query(value = "UPDATE orders set orders.current_status = :status where orders.id = :order_id",nativeQuery = true)
    public void updateCurrentStatus(@Param("status") Integer status,@Param("order_id") BigInteger order_id);

}
