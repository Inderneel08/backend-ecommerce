package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_ecommerce.Models.Users;

public interface UserRepository extends JpaRepository<Users,BigInteger>{
    
    @Query(value = "SELECT * FROM users where email = :email",nativeQuery = true)
    public Users findByemail(@Param("email") String email);
}
