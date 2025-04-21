package com.example.backend_ecommerce.Models;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "order_items")
@Entity
@Getter
@Setter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "product_id")
    private BigInteger product_id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "order_id")
    private BigInteger order_id;

    @Column(name = "cart_id")
    private BigInteger cart_id;
}
