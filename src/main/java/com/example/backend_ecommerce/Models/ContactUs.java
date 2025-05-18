package com.example.backend_ecommerce.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "contactus")
@Getter
@Setter
public class ContactUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "message")
    private String message;

    @Column(name = "email_status")
    private Integer email_status;

    @Column(name = "generated_query_no")
    private String generated_query_no;

    @Column(name = "status_request")
    private Integer status_request;
}
