package com.example.backend_ecommerce.ServiceLayer;

import com.example.backend_ecommerce.Models.AboutUs;
import com.example.backend_ecommerce.RepositoryLayer.AboutUsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class AboutUsServiceLayer {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    public List<AboutUs> getAboutUs()
    {
        return(aboutUsRepository.findAll());
    }

    @Transactional
    public boolean updateAboutUs(@RequestBody Map<String,Object> requestBody)
    {
        String newContent = (String) requestBody.get("newContent");

        if(newContent!=null){
            System.out.println("Hello111");
            newContent = newContent.replace("&nbsp;", " ").trim();

            List<AboutUs> aboutUs = aboutUsRepository.findAll();

            if(aboutUs.isEmpty()){
                return(false);
            }

            try{
                aboutUsRepository.updateAboutUsText(aboutUs.get(0).getId(),newContent);

                return(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return(false);
    }

}
