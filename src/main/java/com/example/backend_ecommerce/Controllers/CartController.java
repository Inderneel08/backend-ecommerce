package com.example.backend_ecommerce.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/auth/getCartInfo")
    public ResponseEntity<?> getCartInfo()
    {
        
    }
}
