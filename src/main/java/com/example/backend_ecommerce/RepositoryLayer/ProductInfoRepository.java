package com.example.backend_ecommerce.RepositoryLayer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.example.backend_ecommerce.Models.Products;
import org.springframework.stereotype.Repository;

import com.example.backend_ecommerce.Models.ProductInfoDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

@Repository
public class ProductInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductInfoDTO> getProductInfo(BigInteger id)
    {
        String sql = "SELECT products.id, products.title, products.price,products.description, products.image, rating.rate, category.id,category.category_name FROM products INNER JOIN rating ON products.rate_id=rating.id INNER JOIN category ON products.category=category.id WHERE products.id = :id";

        Query query = entityManager.createNativeQuery(sql, Tuple.class);

        query.setParameter("id", id);

        List<Tuple> results = query.getResultList();

        List<ProductInfoDTO> productInfoDTOs = new ArrayList<>();

        for (Tuple tuple : results) {
            BigInteger productId = BigInteger.valueOf(tuple.get(0, Long.class));
            String title = tuple.get(1, String.class);
            BigDecimal price = (BigDecimal) tuple.get(2);
            String description = tuple.get(3, String.class);
            String image = tuple.get(4, String.class);
            BigDecimal rate =(BigDecimal) tuple.get(5);
            Integer categoryId = (Integer) tuple.get(6);
            String categoryName = tuple.get(7, String.class);

            ProductInfoDTO productInfoDTO = new ProductInfoDTO(productId,title,price,description,image,rate,categoryId,categoryName);

            productInfoDTOs.add(productInfoDTO);
        }

        return(productInfoDTOs);
    }

    public List<Products> getProducts()
    {
        String sql = "select products.* from count_table inner JOIN products on count_table.product_id = products.id";

        Query query = entityManager.createNativeQuery(sql, Tuple.class);

        List<Tuple> results = query.getResultList();

        List<Products> products = new ArrayList<>();

        if(!results.isEmpty()){
            for(Tuple tuple : results){
                BigInteger id = BigInteger.valueOf(tuple.get(0, Long.class));

                String title = tuple.get(1, String.class);

                BigDecimal price = (BigDecimal) tuple.get(2);

                String description = tuple.get(3, String.class);

                Integer category = (Integer) tuple.get(4);

                String image = tuple.get(5, String.class);

                BigInteger rate_id = BigInteger.valueOf(tuple.get(6, Long.class));

                products1.setId(id);

                products1.setTitle(title);

                products1.setPrice(price);

                products1.setDescription(description);

                products1.setCategory(category);
            }
        }

        return(products);
    }
    
}
