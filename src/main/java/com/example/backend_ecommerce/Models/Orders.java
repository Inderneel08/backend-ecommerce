package com.example.backend_ecommerce.Models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "cf_order_id")
    private String cf_order_id;

    @Column(name = "order_amount",precision = 10,scale = 2)
    private BigDecimal order_amount;

    @Column(name = "payment_session_id")
    private String payment_session_id;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "table_name")
    private String table_name;

    @Column(name = "table_id")
    private BigInteger table_id;

    @Column(name = "current_status")
    private Integer current_status;
}
