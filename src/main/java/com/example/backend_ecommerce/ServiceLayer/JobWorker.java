package com.example.backend_ecommerce.ServiceLayer;

import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.PaymentEntity;
import com.example.backend_ecommerce.Models.OrderItems;
import com.example.backend_ecommerce.Models.Orders;
import com.example.backend_ecommerce.RepositoryLayer.CartRepository;
import com.example.backend_ecommerce.RepositoryLayer.OrderItemRepository;
import com.example.backend_ecommerce.RepositoryLayer.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobWorker {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private final String xApiVersion = "2023-08-01";

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void processJobs()
    {
        Cashfree.XClientId = "TEST10284318f45007527c473d39477181348201";

        Cashfree.XClientSecret = "cfsk_ma_test_0c6869d946184130107fc0cb23acbabc_4e020c8f";

        Cashfree cashfree = new Cashfree();

        List<Orders> orders = orderRepository.findOrdersByCurrentStatus(0);

        if(!orders.isEmpty()){

            try{
                for(Orders order : orders){
                    ApiResponse<List<PaymentEntity>> result = cashfree.PGOrderFetchPayments(this.xApiVersion,order.getOrder_id(),null,null,null);

                    List<PaymentEntity> paymentEntity = result.getData();

                    if(!paymentEntity.isEmpty()){

                        PaymentEntity payment = paymentEntity.get(0);

                        if(payment.getPaymentStatus()!=null){
                            String status = payment.getPaymentStatus().getValue();

                            if(status.equals("SUCCESS")){
                                orderRepository.updateCurrentStatus(1,order.getId());
                            }
                            else{
                                orderRepository.updateCurrentStatus(-1,order.getId());
                            }

                            if(order.getTable_name().equals("users")){
                                List<OrderItems> orderItems = orderItemRepository.getOrderItemsByOrderId(order.getId());

                                for(OrderItems orderItem : orderItems){
                                    cartRepository.deleteById(orderItem.getCart_id());
                                }
                            }
                        }
                    }
                    else{
                        orderRepository.updateCurrentStatus(-1,order.getId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ;
    }

}
