package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        return (products);
    }

    public List<Products> findProductsById(BigInteger id) {
        List<Products> products = new ArrayList<>(productRepository.findById(id)
                .map(Collections::singletonList).orElse(Collections.emptyList()));

        return (products);
    }

    public List<Products> findProductsByCategoryId(Integer category_id) {
        List<Products> products = new ArrayList<>(productRepository.findByCategory(category_id));

        return(products);
    }

}
