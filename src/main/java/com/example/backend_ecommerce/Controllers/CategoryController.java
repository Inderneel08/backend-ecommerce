package com.example.backend_ecommerce.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @GetMapping("/api/getAll/categories")
    public ResponseEntity<?> getAllCategories() {

    }

}
