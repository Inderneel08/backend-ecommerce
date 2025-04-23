package com.example.backend_ecommerce.ServiceLayer;

import org.springframework.stereotype.Service;

import com.example.backend_ecommerce.Models.TokenTable;

@Service
public class JwtTokenServiceLayer {
    
    public TokenTable createToken()
    {
        TokenTable token = new TokenTable();

        return(token);
    }
}
