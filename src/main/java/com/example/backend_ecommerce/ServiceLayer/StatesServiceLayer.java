package com.example.backend_ecommerce.ServiceLayer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_ecommerce.Models.States;
import com.example.backend_ecommerce.RepositoryLayer.StatesRepository;

@Service
public class StatesServiceLayer {

    @Autowired    
    private StatesRepository statesRepository;

    public List<States> getAllStates()
    {
        List<States> states = new ArrayList<>(statesRepository.findAll());

        return(states);
    }
}
