package com.example.backend_ecommerce.Controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.ServiceLayer.ProductServiceLayer;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceLayer productServiceLayer;

    @GetMapping("/api/products")
    public ResponseEntity<?> getProductInfo(@RequestParam(value = "id", required = false) BigInteger id) {

        if (id == null) {
            return (ResponseEntity.ok().body(productServiceLayer.findProducts()));
        }

        return (ResponseEntity.ok().body(productServiceLayer.findProductsById(id)));
    }

    @GetMapping("/api/products/category")
    public ResponseEntity<?> getProductsCategory(@RequestParam(value = "id") Integer id) {
        return (ResponseEntity.ok().body(productServiceLayer.findProductsByCategoryId(id)));
    }

}
