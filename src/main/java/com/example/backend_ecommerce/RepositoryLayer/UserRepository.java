package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_ecommerce.Models.Users;

public interface UserRepository extends JpaRepository<Users,BigInteger>{
    
    @Query(value = "SELECT * FROM users where email = :email",nativeQuery = true)
    public Users findByemail(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE users set users.password = :password where users.id = :userId",nativeQuery = true)
    public void savePassword(@Param("userId") BigInteger userId,@Param("password") String password);

    @Modifying
    @Query(value = "UPDATE users set users.phone = :phone, users.state = :state , users.address = :address, users.zip = :zip where users.id = :userId ",nativeQuery = true)
    public void updateProfile(@Param("userId") BigInteger userId,@Param("phone") String phone,@Param("address") String address,@Param("state") BigInteger state,@Param("zip") String zip);
}
