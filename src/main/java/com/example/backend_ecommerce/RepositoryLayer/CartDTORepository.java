package com.example.backend_ecommerce.RepositoryLayer;

import com.example.backend_ecommerce.Models.CartDTO;
import com.example.backend_ecommerce.Models.ProductInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDTORepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CartDTO> getCartItems(BigInteger userId)
    {
        String sql = "SELECT products.id as product_id,products.title,products.image,products.price,cart.count,cart.id as cartId FROM cart inner join products on products.id=cart.product_id where cart.user_id = :userId";

        Query query = entityManager.createNativeQuery(sql, Tuple.class);

        query.setParameter("userId", userId);

        List<Tuple> results = query.getResultList();

        List<CartDTO> cartDTOS = new ArrayList<>();

        for (Tuple tuple : results) {
            BigInteger product_id = BigInteger.valueOf(tuple.get(0, Long.class));

            String title = tuple.get(1, String.class);

            String image = tuple.get(2, String.class);

            BigDecimal price = (BigDecimal) tuple.get(3);

            Integer count = (Integer) tuple.get(4);

            BigInteger cartId = BigInteger.valueOf(tuple.get(5, Long.class));

            CartDTO cartDTO = new CartDTO();

            cartDTO.setProduct_id(product_id);

            cartDTO.setTitle(title);

            cartDTO.setImage(image);

            cartDTO.setPrice(price);

            cartDTO.setCount(count);

            cartDTO.setCartId(cartId);

            cartDTOS.add(cartDTO);
        }

        return(cartDTOS);
    }

}
