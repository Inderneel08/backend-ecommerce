package com.example.backend_ecommerce.Controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.ServiceLayer.JwtTokenServiceLayer;
import com.example.backend_ecommerce.ServiceLayer.MyUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,HttpServletResponse response)
    {
        if(!jwtTokenServiceLayer.deleteToken((String)request.getAttribute("hashedToken"))){
            return(ResponseEntity.badRequest().body("Some error happened!"));
        }

        Cookie cookie = new Cookie("token", null); // Clear the token
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return(ResponseEntity.ok().body("Logout successfull!"));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/auth/check")
    public ResponseEntity<?> checkUser()
    {
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("message","You are authenticated");

        return(ResponseEntity.ok().body(hashMap));
    }


}
