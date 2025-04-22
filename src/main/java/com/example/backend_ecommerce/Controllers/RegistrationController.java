package com.example.backend_ecommerce.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.ServiceLayer.MyUserDetailsService;

@RestController
public class RegistrationController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, Object> request) {
        System.out.println(request);

        return (ResponseEntity.ok().build());
    }
}
