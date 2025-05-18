package com.example.backend_ecommerce.Controllers;

import com.example.backend_ecommerce.ServiceLayer.QueryServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    private QueryServiceLayer queryServiceLayer;

    @PostMapping("/api/auth/createQuery")
    public ResponseEntity<?> createQuery(@RequestBody Map<String,Object> requestBody)
    {
        if(!queryServiceLayer.createQuery(requestBody)){
            return(ResponseEntity.badRequest().body("Error creating query"));
        }

        return(ResponseEntity.ok().body("Query created successfully"));
    }
}
