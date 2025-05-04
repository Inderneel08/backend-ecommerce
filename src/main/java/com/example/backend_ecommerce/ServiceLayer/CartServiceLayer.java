package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.example.backend_ecommerce.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend_ecommerce.Models.Cart;
import com.example.backend_ecommerce.Models.CartDTO;
import com.example.backend_ecommerce.RepositoryLayer.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartServiceLayer {

    @Autowired
    private CartRepository cartRepository;

    public Integer cartCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        return (cartRepository.countCartItems(users.getId()));
    }

    public List<CartDTO> getCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        return (cartRepository.getCartItems(users.getId()));
    }

    public BigDecimal computeTotalCost(List<CartDTO> cartDTOs) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (CartDTO cartDTO : cartDTOs) {
            BigDecimal itemTotal = cartDTO.getPrice().multiply(BigDecimal.valueOf(cartDTO.getCount()));

            totalCost.add(itemTotal);
        }

        return (totalCost);
    }

    @Transactional
    public boolean removeFromCart(BigInteger userId, @RequestBody Map<String, Object> requestBody) {

        try {
            BigInteger product_id = (BigInteger) requestBody.get("id");

            Cart cart = cartRepository.getCartByProductIdUserId(userId,product_id);

            if(cart.getCount()>1){
                cartRepository.decreaseCartCount(cart.getId(),cart.getCount()-1);
            }else{
                cartRepository.deleteById(cart.getId());
            }
        } catch (Exception e) {
            return (false);
        }

        return (true);
    }

}
