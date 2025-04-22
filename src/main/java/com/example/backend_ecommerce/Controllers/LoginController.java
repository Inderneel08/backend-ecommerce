package com.example.backend_ecommerce.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String,Object> request)
    {
        System.out.println(request);

        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.get("email"), request.get("password"))
                    );

        System.out.println(authentication);

        return(null);
    }
}
