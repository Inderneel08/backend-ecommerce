package com.example.backend_ecommerce.Controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

}
