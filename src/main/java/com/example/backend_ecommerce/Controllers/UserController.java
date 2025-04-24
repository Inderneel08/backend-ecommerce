package com.example.backend_ecommerce.Controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/api/auth/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String,Object> requestBody){
        return(ResponseEntity.ok().build());
    }
}
