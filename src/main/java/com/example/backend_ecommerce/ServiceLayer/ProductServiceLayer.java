package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.backend_ecommerce.Models.ProductInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_ecommerce.Models.Products;
import com.example.backend_ecommerce.RepositoryLayer.ProductRepository;

@Service
public class ProductServiceLayer {

    @Autowired
    private ProductRepository productRepository;

    public List<Products> findProducts() {
        List<Products> products = new ArrayList<>(productRepository.findAll());

        for(Products product : products){
            product.setImage("http://localhost:8080/".concat(product.getImage()));
        }

        return (products);
    }

    public List<Products> getProductsCategory(Integer category)
    {
        List<Products> products = productRepository.findByCategory(category);

        for(Products product : products){
            product.setImage("http://localhost:8080/".concat(product.getImage()));
        }

        return(products);
    }


    public List<ProductInfoDTO> findProductsById(BigInteger id) {
        List<ProductInfoDTO> products = productRepository.getProductInfo(id);

        for(ProductInfoDTO product : products){
            product.setImage("http://localhost:8080/".concat(product.getImage()));
        }

        return (products);
    }

    public List<Products> findProductsByCategoryId(Integer category_id) {
        List<Products> products = new ArrayList<>(productRepository.findByCategory(category_id));

        return(products);
    }

}
