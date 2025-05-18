package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;
import java.util.List;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
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
    @Query("UPDATE Orders O set O.current_status = :status where O.id = :order_id")
    public void updateCurrentStatus(@Param("status") Integer status,@Param("order_id") BigInteger order_id);

    @Modifying
    @Query(value = "UPDATE orders set orders.current_status = :status where orders.payment_session_id = :payment_session_id",nativeQuery = true)
    public void updateCurrentStatusViaSessionId(@Param("status") Integer status,@Param("payment_session_id") String payment_session_id);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("SELECT O from Orders O where O.id = :order_id")
//    public Orders lockOrders(@Param("order_id") BigInteger order_id);

    @Query(value = "SELECT * FROM orders where orders.current_status = :current_status",nativeQuery = true)
    public List<Orders> findOrdersByCurrentStatus(@Param("current_status") Integer current_status);

    @Query(value = "SELECT * FROM orders where orders.payment_session_id = :paymentSessionId",nativeQuery = true)
    public Orders findByPaymentSessionId(@Param("paymentSessionId") String paymentSessionId);

}
