package com.example.backend_ecommerce.Controllers;

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
import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.ServiceLayer.MyUserDetailsService;

@RestController
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/api/auth/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String,Object> requestBody){
        if(!myUserDetailsService.updatePassword((String) requestBody.get("password"))){
            return(ResponseEntity.badRequest().body("Password updation failed!"));
        }

        return(ResponseEntity.ok().body("Password updated successfully!"));
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/api/auth/findProfileInfo")
    public ResponseEntity<?> getProfileInfo()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        return(ResponseEntity.ok().body(users));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/api/auth/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String,Object> requestBody)
    {
        myUserDetailsService.updateProfile(requestBody);

        return(ResponseEntity.ok().body("Profile updated successfully!"));
    }

}
