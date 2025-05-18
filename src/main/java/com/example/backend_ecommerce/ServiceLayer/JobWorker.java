package com.example.backend_ecommerce.ServiceLayer;

import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.PaymentEntity;
import com.example.backend_ecommerce.Models.ContactUs;
import com.example.backend_ecommerce.Models.OrderItems;
import com.example.backend_ecommerce.Models.Orders;
import com.example.backend_ecommerce.RepositoryLayer.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class JobWorker {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Autowired
    private EmailService emailService;

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

    public String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.trim().getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating SHA-256 hash", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }


    public String generatedQueryNo(ContactUs contactUs)
    {
        return(contactUs.getId() + "_" + sha256(contactUs.getName()) + "_" + sha256(contactUs.getEmail()) + "_" + sha256(contactUs.getMessage()));
    }

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void processContactUsEmails()
    {
        try{
            List<ContactUs> contactUs = contactUsRepository.getContactUsEmailStatus(0);

            for (ContactUs contactUs1 : contactUs){
                contactUs1.setGenerated_query_no(generatedQueryNo(contactUs1));

                contactUs1.setEmail_status(1);

                contactUsRepository.save(contactUs1);

                emailService.sendMail(contactUs1.getEmail(),"Query generated with query no as ".concat(contactUs1.getGenerated_query_no()),"Your query with query no as ".concat(contactUs1.getGenerated_query_no()).concat(" has been generated.If a solution has been found our team will contact you."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ;
    }

    @Transactional
    @Scheduled(fixedDelay = 15000)
    public void loadCountTable()
    {
        try{

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
