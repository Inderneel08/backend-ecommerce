package com.example.backend_ecommerce.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class ProductInfoDTO {
    private BigInteger product_id;

    private String title;

    private BigDecimal price;

    private String description;

    private String image;

    private BigDecimal rate;

    private Integer category_id;

    private String category_name;

//    ProductInfoDTO(BigInteger product_id,String title,Double price,String description,String image,Double rate,Integer category_id,String category_name)
//    {
//        this.product_id=product_id;
//
//        this.title=title;
//
//        this.price=price;
//
//        this.description=description;
//
//        this.image=image;
//
//        this.rate=rate;
//
//        this.category_id=category_id;
//
//        this.category_name=category_name;
//    }

}
