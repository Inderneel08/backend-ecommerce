package com.example.backend_ecommerce.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "count_table")
@Getter
@Setter
public class CountTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "product_id")
    private BigInteger product_id;
}
