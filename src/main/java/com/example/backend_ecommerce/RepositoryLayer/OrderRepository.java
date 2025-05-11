package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;
import java.util.List;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.Orders;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders,BigInteger>{

    @Query(value = "SELECT * FROM orders where orders.table_id = :userId and orders.current_status = :current_status",nativeQuery = true)
    public List<Orders> fetchOrdersByStatusAndTableId(@Param("userId") BigInteger userId ,@Param("current_status") Integer current_status);

    @Modifying
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "UPDATE orders set orders.current_status = :status where orders.id = :order_id",nativeQuery = true)
    public void updateCurrentStatus(@Param("status") Integer status,@Param("order_id") BigInteger order_id);

    @Query(value = "SELECT * FROM orders where orders.current_status = :current_status",nativeQuery = true)
    public List<Orders> findOrdersByCurrentStatus(@Param("current_status") Integer current_status);

}
