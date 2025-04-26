package com.example.backend_ecommerce.Models;

import java.math.BigInteger;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "product_id")
    private BigInteger product_id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "user_id")
    private BigInteger user_id;
}
