package com.example.backend_ecommerce.Models;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private BigInteger id;

    private String title;

    private String image;

    private BigDecimal price;

    private Integer count;
}
