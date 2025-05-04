package com.example.backend_ecommerce.RepositoryLayer;

import com.example.backend_ecommerce.Models.Anonymus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface AnonymusRepository extends JpaRepository<Anonymus, BigInteger> {

    @Query(value = "SELECT * FROM anonymus where anonymus.email = :email",nativeQuery = true)
    public Anonymus getUserByEmail(@Param("email") String email);
}
