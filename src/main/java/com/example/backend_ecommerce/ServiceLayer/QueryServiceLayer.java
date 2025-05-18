package com.example.backend_ecommerce.ServiceLayer;

import com.example.backend_ecommerce.Models.ContactUs;
import com.example.backend_ecommerce.RepositoryLayer.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class QueryServiceLayer {

    @Autowired
    private ContactUsRepository contactUsRepository;

    public boolean createQuery(@RequestBody Map<String,Object> requestBody)
    {
        String name = (String) requestBody.get("name");

        String email = (String) requestBody.get("email");

        String message = (String) requestBody.get("message");

        ContactUs contactUs = new ContactUs();

        contactUs.setName(name);

        contactUs.setEmail(email);

        contactUs.setMessage(message);

        contactUs.setEmail_status(0);

        try{
            contactUsRepository.save(contactUs);
        } catch (Exception e) {
            e.printStackTrace();

            return(false);
        }

        return(true);
    }

}
