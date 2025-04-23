package com.example.backend_ecommerce.Controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.ServiceLayer.JwtTokenServiceLayer;
import com.example.backend_ecommerce.ServiceLayer.MyUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtTokenServiceLayer jwtTokenServiceLayer;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> request, HttpServletResponse response) {
        Users users = (Users) myUserDetailsService.loadUserByUsername((String) request.get("email"));

        if (users == null) {
            return (ResponseEntity.badRequest().body("Email Id or Password incorrect!"));
        }

        String token = jwtTokenServiceLayer.createToken(users.getId());

        Cookie cookie = new Cookie("token", token);

        cookie.setHttpOnly(true);

        cookie.setSecure(false); // if using HTTPS

        cookie.setMaxAge(3600); // 1 hour

        response.addCookie(cookie);

        return (ResponseEntity.ok().build());
    }
}
