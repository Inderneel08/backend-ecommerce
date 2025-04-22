package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.States;

public interface StatesRepository extends JpaRepository<States,BigInteger>{
    
}
