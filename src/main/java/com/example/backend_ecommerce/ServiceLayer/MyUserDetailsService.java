package com.example.backend_ecommerce.ServiceLayer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.RepositoryLayer.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userRepository.findByemail(username);

        return(users);
    }

    public boolean createUser(@RequestBody Map<String,Object> body)
    {
        System.out.println(body);

        String email = (String) body.get("email");

        String name = (String) body.get("name");

        String password = (String) body.get("password");

        

        return(true);
    }
}
