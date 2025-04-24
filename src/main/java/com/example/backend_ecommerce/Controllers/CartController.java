package com.example.backend_ecommerce.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.ServiceLayer.CartServiceLayer;

@RestController
public class CartController {

    @Autowired
    private CartServiceLayer cartServiceLayer;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/auth/getCartInfo")
    public ResponseEntity<?> getCartInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        Integer cartCount = cartServiceLayer.cartCount(users.getId());

        return (ResponseEntity.ok().body(cartCount));
    }
}
