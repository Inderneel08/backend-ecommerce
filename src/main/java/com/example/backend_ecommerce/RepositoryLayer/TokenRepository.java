package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_ecommerce.Models.TokenTable;

public interface TokenRepository extends JpaRepository<TokenTable,BigInteger>{

    @Query(value = "SELECT * FROM token_table where token_table.token_value = :token_value",nativeQuery = true)
    public TokenTable findByTokenValue(@Param("token_value") String token_value);

}
