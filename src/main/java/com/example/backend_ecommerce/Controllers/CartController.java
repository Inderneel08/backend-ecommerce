package com.example.backend_ecommerce.Controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.Models.CartDTO;
import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.ServiceLayer.CartServiceLayer;

@RestController
public class CartController {

    @Autowired
    private CartServiceLayer cartServiceLayer;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/auth/getCartInfo")
    public ResponseEntity<?> getCartInfo() {
        Integer cartCount = cartServiceLayer.cartCount();

        return (ResponseEntity.ok().body(cartCount));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/auth/getCartItems")
    public ResponseEntity<?> getCartItems() {
        List<CartDTO> cartDTOs = cartServiceLayer.getCartItems();

        BigDecimal totalCost = cartServiceLayer.computeTotalCost(cartDTOs);

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("cartItems", cartDTOs);

        hashMap.put("totalCost", totalCost);

        return (ResponseEntity.ok().body(hashMap));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/auth/removeFromCart")
    public ResponseEntity<?> removeFromCart(@RequestBody Map<String,Object> requestBody){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        try {
            cartServiceLayer.removeFromCart(users.getId(),requestBody);
        } catch (Exception e) {
            return(ResponseEntity.badRequest().body("Some error while deleting item from the cart!"));
        }

        return(ResponseEntity.ok().body("Item successfully removed from cart!"));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/auth/addToCart")
    public ResponseEntity<?> addToCart()
    {
        return (ResponseEntity.ok().build());
    }

}
