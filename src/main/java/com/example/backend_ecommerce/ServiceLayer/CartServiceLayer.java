package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend_ecommerce.RepositoryLayer.CartRepository;

@Service
public class CartServiceLayer {

    @Autowired
    private CartRepository cartRepository;

    public Integer cartCount(BigInteger userId)
    {
        return(cartRepository.countCartItems(userId));
    }
}
