package com.example.backend_ecommerce.ServiceLayer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_ecommerce.Models.Category;
import com.example.backend_ecommerce.Models.Products;
import com.example.backend_ecommerce.RepositoryLayer.CategoryRepository;

@Service
public class CategoryServiceLayer {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>(categoryRepository.findAll());

        return (categories);
    }

}
