package com.example.backend_ecommerce.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fakeemails")
public class FakeEmails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "email")
    private String email;

    @Column(name = "count_sent")
    private Integer count_sent;
}
