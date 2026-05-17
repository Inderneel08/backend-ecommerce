package com.example.backend_ecommerce.RepositoryLayer;

import com.example.backend_ecommerce.Models.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface AboutUsRepository extends JpaRepository<AboutUs, BigInteger> {

    @Modifying
    @Query(value = "UPDATE aboutus set aboutus.about_us_text = :aboutUs where aboutUs.id = :id",nativeQuery = true)
    public void updateAboutUsText(@Param("id") BigInteger id,@Param("aboutUs") String aboutUs);

}
