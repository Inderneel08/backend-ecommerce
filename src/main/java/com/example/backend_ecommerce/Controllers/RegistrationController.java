package com.example.backend_ecommerce.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
        // System.out.println(request);

        String email = (String) request.get("email");

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);

        if(userDetails!=null){
            return(ResponseEntity.badRequest().body("User already exists!"));
        }
        else{
            myUserDetailsService.createUser(request);
        }

        return (ResponseEntity.ok().body("User created successfully!"));
    }
}
