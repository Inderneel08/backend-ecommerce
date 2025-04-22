package com.example.backend_ecommerce.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.ServiceLayer.CategoryServiceLayer;

@RestController
public class CategoryController {

    @Autowired
    private CategoryServiceLayer categoryServiceLayer;

    @GetMapping("/api/getAll/categories")
    public ResponseEntity<?> getAllCategories() {
        return(ResponseEntity.ok().body(categoryServiceLayer.getAllCategories()));
    }

}
