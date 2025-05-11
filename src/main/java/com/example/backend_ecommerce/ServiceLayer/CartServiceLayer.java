package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.RepositoryLayer.CartDTORepository;
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

    @Autowired
    private CartDTORepository cartDTORepository;

    public Integer cartCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        return (cartRepository.countCartItems(users.getId()));
    }

    public List<CartDTO> getCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

//        return (cartRepository.getCartItems(users.getId()));

        return(cartDTORepository.getCartItems(users.getId()));
    }

    public BigDecimal computeTotalCost(List<CartDTO> cartDTOs) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (CartDTO cartDTO : cartDTOs) {
            BigDecimal itemTotal = cartDTO.getPrice().multiply(BigDecimal.valueOf(cartDTO.getCount()));

            totalCost = totalCost.add(itemTotal);
        }

        return (totalCost);
    }

    @Transactional
    public boolean removeFromCart(@RequestBody Map<String, Object> requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        try {
            Optional<Cart> cart = cartRepository.findById(BigInteger.valueOf((Integer) requestBody.get("cartId")));

            if(cart.get().getCount()>1){
                cartRepository.decreaseCartCount(cart.get().getId(), cart.get().getCount()-1);
            }else{
                cartRepository.deleteById(cart.get().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();

            return (false);
        }

        return (true);
    }


    @Transactional
    public boolean addToCart(@RequestBody Map<String,Object> requestBody)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        BigInteger product_id = BigInteger.valueOf(((Integer) requestBody.get("product_id")).longValue());

        Optional<Cart> cart = cartRepository.findById(BigInteger.valueOf((Integer) requestBody.get("cartId")));

        try{
            if(cart.isPresent()){
                cartRepository.increaseCartCount(cart.get().getId(), cart.get().getCount()+1);
            }
            else{
                Cart newCart = new Cart();

                newCart.setProduct_id(product_id);

                newCart.setCount(1);

                newCart.setUser_id(users.getId());

                newCart.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

                cartRepository.save(newCart);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return(false);
        }

        return(true);
    }

    @Transactional
    public boolean addToCartViaProductId(@RequestBody Map<String,Object> requestBody)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        BigInteger product_id = BigInteger.valueOf(((Integer) requestBody.get("id")).longValue());

        Cart cart = cartRepository.getCartByProductIdUserId(users.getId(),product_id);

        if(cart==null){
            cart = new Cart();

            cart.setCount(1);

            cart.setProduct_id(product_id);

            cart.setUser_id(users.getId());

            cart.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

            cartRepository.save(cart);
        }
        else{
            cartRepository.increaseCartCount(cart.getId(),cart.getCount()+1);
        }

        return(true);
    }

}
