package com.example.backend_ecommerce.Controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.Models.Orders;
import com.example.backend_ecommerce.ServiceLayer.PaymentServiceLayer;

@RestController
public class PaymentController {

    @Autowired
    private PaymentServiceLayer paymentServiceLayer;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/auth/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> requestBody) {

        Orders orders = paymentServiceLayer.createOrder(requestBody);

        if (orders == null) {
            return (ResponseEntity.badRequest().body("Some error in creating the order"));
        }

        return (ResponseEntity.ok().body(orders.getPayment_session_id()));
    }


    // {
//     "email":"test123@yopmail.com",
//     "password":"5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975"
// }

    // {
//     "subtotal":"100"
// }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/auth/processPendingOrders")
    public ResponseEntity<?> processPendingOrders() {

        if(!paymentServiceLayer.processPendingOrders()){
            return(ResponseEntity.badRequest().build());
        }

        return (ResponseEntity.ok().build());
    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/api/auth/hello")
//    public  ResponseEntity<?> hello()
//    {
//        return(ResponseEntity.ok().build());
//    }

}
