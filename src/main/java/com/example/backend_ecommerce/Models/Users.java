package com.example.backend_ecommerce.Models;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "users")
@Getter
@Setter
@Entity
public class Users implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Integer role;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "jwt_token")
    private String jwt_token;

    @Column(name = "last_login")
    private Timestamp last_login;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private BigInteger state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone")
    private String phone;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = (this.role == 0) ? "ROLE_USER" : "ROLE_ADMIN";
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }


    @Override
    public String getUsername() {
        return(this.email);
    }
}
