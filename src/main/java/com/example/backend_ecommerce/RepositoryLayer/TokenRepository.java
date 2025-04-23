package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.TokenTable;

public interface TokenRepository extends JpaRepository<TokenTable,BigInteger>{
    
}
