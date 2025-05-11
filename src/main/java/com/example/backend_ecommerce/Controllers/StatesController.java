package com.example.backend_ecommerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ecommerce.Models.States;
import com.example.backend_ecommerce.ServiceLayer.StatesServiceLayer;

@RestController
public class StatesController {
    
    @Autowired
    private StatesServiceLayer statesServiceLayer;

    @GetMapping("/api/auth/getStates")
    public List<States> getStates()
    {
        return(statesServiceLayer.getAllStates());
    }
}
