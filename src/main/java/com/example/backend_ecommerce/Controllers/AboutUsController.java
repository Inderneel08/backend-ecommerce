package com.example.backend_ecommerce.Controllers;

import com.example.backend_ecommerce.ServiceLayer.AboutUsServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AboutUsController {

    @Autowired
    private AboutUsServiceLayer aboutUsServiceLayer;

    @GetMapping("/api/auth/getAboutUs")
    public ResponseEntity<?> getAboutUs()
    {
        return(ResponseEntity.ok().body(aboutUsServiceLayer.getAboutUs()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/auth/updateAboutUs")
    public ResponseEntity<?> postUpdateStatus(@RequestBody Map<String,Object> requestBody)
    {
        if(aboutUsServiceLayer.updateAboutUs(requestBody)){
            return(ResponseEntity.ok().build());
        }

        return(ResponseEntity.badRequest().build());
    }

}
