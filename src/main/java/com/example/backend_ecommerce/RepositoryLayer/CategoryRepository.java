package com.example.backend_ecommerce.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ecommerce.Models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
