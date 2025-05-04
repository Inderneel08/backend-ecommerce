package com.example.backend_ecommerce.Controllers;

import java.math.BigInteger;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.ServiceLayer.ProductServiceLayer;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceLayer productServiceLayer;

    @GetMapping("/api/auth/products/{id}")
    public ResponseEntity<?> getProductInfo(@PathVariable(value = "id", required = false) BigInteger id) {
        HashMap<String,Object> hashMap = new HashMap<>();
        if (id == null) {
            hashMap.put("products",productServiceLayer.findProducts());

            return (ResponseEntity.ok().body(hashMap));
        }
        else{
            hashMap.put("productInfo",productServiceLayer.findProductsById(id));
        }

        return (ResponseEntity.ok().body(hashMap));
    }


    @GetMapping("/api/auth/products/category/{id}")
    public ResponseEntity<?> getProductsCategory(@PathVariable Integer id)
    {
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("productInfoCategory",productServiceLayer.getProductsCategory(id));

        return(ResponseEntity.ok().body(hashMap));
    }


//    @GetMapping("/api/auth/products/category")
//    public ResponseEntity<?> getProductsCategory(@RequestParam(value = "id") Integer id) {
//        return (ResponseEntity.ok().body(productServiceLayer.findProductsByCategoryId(id)));
//    }

}
