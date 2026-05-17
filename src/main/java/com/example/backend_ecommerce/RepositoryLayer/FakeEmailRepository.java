package com.example.backend_ecommerce.RepositoryLayer;

import com.example.backend_ecommerce.Models.FakeEmails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FakeEmailRepository extends JpaRepository<FakeEmails,Integer> {
}
