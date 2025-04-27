package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.CreateOrderRequest;
import com.cashfree.model.CustomerDetails;
import com.cashfree.model.OrderEntity;
import com.example.backend_ecommerce.Models.CartDTO;
import com.example.backend_ecommerce.Models.OrderItems;
import com.example.backend_ecommerce.Models.Orders;
import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.RepositoryLayer.OrderItemRepository;
import com.example.backend_ecommerce.RepositoryLayer.OrderRepository;

@Service
public class PaymentServiceLayer {

    @Autowired
    private CartServiceLayer cartServiceLayer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public boolean createOrder(@RequestBody Map<String, Object> requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        Cashfree.XClientId = "TEST10284318f45007527c473d39477181348201";

        Cashfree.XClientSecret = "cfsk_ma_test_0c6869d946184130107fc0cb23acbabc_4e020c8f";

        Cashfree cashfree = new Cashfree();

        String xApiVersion = "2023-08-01";

        Integer userType = 0;

        CustomerDetails customerDetails = new CustomerDetails();

        double subtotal = Double.parseDouble((String) requestBody.get("subtotal"));

        String formattedAmount = String.format("%.2f", subtotal);

        if (users != null) {
            // List<CartDTO> cartItems = null;
            // cartItems = cartServiceLayer.getCartItems(users.getId());

            customerDetails.setCustomerId("cashfree_".concat(users.getId().toString()));

            customerDetails.setCustomerEmail(users.getEmail());

            customerDetails.setCustomerPhone(users.getPhone());
        } else {
            userType = 1;
        }

        CreateOrderRequest request = new CreateOrderRequest();

        request.setOrderCurrency("INR");

        request.setOrderAmount(Double.parseDouble(formattedAmount));

        request.setCustomerDetails(customerDetails);

        try {
            ApiResponse<OrderEntity> result = cashfree.PGCreateOrder(xApiVersion, request, null, null, null);

            Orders orders = new Orders();

            orders.setCf_order_id(result.getData().getCfOrderId());

            orders.setOrder_amount(BigDecimal.valueOf(Double.parseDouble(formattedAmount)));

            orders.setPayment_session_id(result.getData().getPaymentSessionId());

            orders.setCurrent_status(0);

            orders.setOrder_id(result.getData().getOrderId());

            orders.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

            if (users != null) {
                orders.setTable_name("users");

                orders.setTable_id(users.getId());
            }

            orderRepository.save(orders);

            if (userType == 0) {
                List<CartDTO> cartDTOs = cartServiceLayer.getCartItems(users.getId());

                for (CartDTO cartDTO : cartDTOs) {
                    OrderItems orderItems = new OrderItems();

                    orderItems.setProduct_id(cartDTO.getProduct_id());

                    orderItems.setCount(cartDTO.getCount());

                    orderItems.setOrder_id(orders.getId());

                    orderItems.setCart_id(cartDTO.getCartId());

                    orderItemRepository.save(orderItems);
                }

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();

            return (false);
        }

        return (true);
    }
}
