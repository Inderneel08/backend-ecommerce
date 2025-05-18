package com.example.backend_ecommerce.RepositoryLayer;

import com.example.backend_ecommerce.Models.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface ContactUsRepository extends JpaRepository<ContactUs, BigInteger> {

    @Query(value = "SELECT * FROM contactus where contactus.email_status = :email_status",nativeQuery = true)
    public List<ContactUs> getContactUsEmailStatus(@Param("email_status") Integer email_status);
}
