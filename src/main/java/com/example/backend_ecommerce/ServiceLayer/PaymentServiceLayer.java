package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cashfree.model.PaymentEntity;
import com.example.backend_ecommerce.Models.*;
import com.example.backend_ecommerce.RepositoryLayer.AnonymusRepository;
import com.example.backend_ecommerce.RepositoryLayer.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.CreateOrderRequest;
import com.cashfree.model.CustomerDetails;
import com.cashfree.model.OrderEntity;
import com.example.backend_ecommerce.RepositoryLayer.OrderItemRepository;
import com.example.backend_ecommerce.RepositoryLayer.OrderRepository;

@Service
public class PaymentServiceLayer {

    @Autowired
    private CartServiceLayer cartServiceLayer;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AnonymusRepository anonymusRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    private final String xApiVersion = "2023-08-01";

    public Orders createOrder(@RequestBody Map<String, Object> requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = (Users) authentication.getPrincipal();

        Cashfree.XClientId = "TEST10284318f45007527c473d39477181348201";

        Cashfree.XClientSecret = "cfsk_ma_test_0c6869d946184130107fc0cb23acbabc_4e020c8f";

        Cashfree cashfree = new Cashfree();

        System.out.println(requestBody.toString());

        Integer userType = 0;

        CustomerDetails customerDetails = new CustomerDetails();

        double subtotal = Double.valueOf((String) requestBody.get("subtotal"));

        String formattedAmount = String.format("%.2f", subtotal);

        CreateOrderRequest request = new CreateOrderRequest();

        request.setOrderCurrency("INR");

        request.setOrderAmount(Double.parseDouble(formattedAmount));

        if (users != null) {
            List<Orders> previousOrders = orderRepository.fetchOrdersByStatusAndTableId(users.getId(),0);

            if(!previousOrders.isEmpty()){
                return(null);
            }

            customerDetails.setCustomerId("cashfree_".concat(users.getId().toString()));

            customerDetails.setCustomerEmail(users.getEmail());

            customerDetails.setCustomerPhone(users.getPhone());

            request.setCustomerDetails(customerDetails);
        } else {
            userType = 1;
        }

        Orders orders = new Orders();

        try {
            ApiResponse<OrderEntity> result = cashfree.PGCreateOrder(this.xApiVersion, request, null, null, null);

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
                List<CartDTO> cartDTOs = cartServiceLayer.getCartItems();

                for (CartDTO cartDTO : cartDTOs) {
                    OrderItems orderItems = new OrderItems();

                    orderItems.setProduct_id(cartDTO.getProduct_id());

                    orderItems.setCount(cartDTO.getCount());

                    orderItems.setOrder_id(orders.getId());

                    orderItems.setCart_id(cartDTO.getCartId());

                    orderItemRepository.save(orderItems);
                }

            } else {
                String email = (String) requestBody.get("email");

                Anonymus anonymus = anonymusRepository.getUserByEmail(email);

                if(anonymus==null) {
                    anonymus = new Anonymus();

                    anonymus.setFirst_name((String) requestBody.get("firstName"));

                    anonymus.setLast_name((String) requestBody.get("lastName"));

                    anonymus.setEmail(email);

                    anonymus.setAddress((String) requestBody.get("address"));

                    anonymus.setAddress2((String) requestBody.get("address2"));

                    anonymus.setState((BigInteger) requestBody.get("currentState"));

                    anonymus.setZip((String) requestBody.get("zip"));

                    anonymus.setPhone((String) requestBody.get("phone"));

                    anonymusRepository.save(anonymus);

                    anonymus = anonymusRepository.getUserByEmail(email);
                }

                orders.setTable_name("anonymus");

                orders.setTable_id(anonymus.getId());

                customerDetails.setCustomerId("cashfree_".concat(String.valueOf(anonymus.getId())));

                customerDetails.setCustomerEmail(email);

                customerDetails.setCustomerPhone(anonymus.getPhone());

                request.setCustomerDetails(customerDetails);
            }

        } catch (Exception e) {
            e.printStackTrace();

            return (null);
        }

        return (orders);
    }

//    @Transactional
//    public boolean processPendingOrders()
//    {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Users users = (Users) authentication.getPrincipal();
//
//        Cashfree.XClientId = "TEST10284318f45007527c473d39477181348201";
//
//        Cashfree.XClientSecret = "cfsk_ma_test_0c6869d946184130107fc0cb23acbabc_4e020c8f";
//
//        Cashfree cashfree = new Cashfree();
//
//        List<Orders> orders = orderRepository.fetchOrdersByStatusAndTableId(users.getId(),0);
//
//        try {
//            for(Orders order: orders){
//                ApiResponse<List<PaymentEntity>> result = cashfree.PGOrderFetchPayments(this.xApiVersion,order.getOrder_id(),null,null,null);
//
//                List<PaymentEntity> paymentEntity = result.getData();
//
//                if(!paymentEntity.isEmpty()){
//
//                    PaymentEntity payment = paymentEntity.get(0);
//
//                    if(payment.getPaymentStatus()!=null){
//                        String status = payment.getPaymentStatus().getValue();
//
//                        if(status.equals("SUCCESS")){
//                            orderRepository.updateCurrentStatus(1,order.getId());
//                        }
//                        else{
//                            orderRepository.updateCurrentStatus(-1,order.getId());
//                        }
//
//                        if(order.getTable_name().equals("users")){
//                            List<OrderItems> orderItems = orderItemRepository.getOrderItemsByOrderId(order.getId());
//
//                            for(OrderItems orderItem : orderItems){
//                                cartRepository.deleteById(orderItem.getCart_id());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//
//            return(false);
//        }
//
//        return(true);
//    }

}
