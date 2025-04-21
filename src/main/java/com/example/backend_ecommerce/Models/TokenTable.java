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

@Table(name = "token_table")
@Entity
@Getter
@Setter
public class TokenTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "token_value")
    private String token_value;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "user_id")
    private BigInteger user_id;
}
