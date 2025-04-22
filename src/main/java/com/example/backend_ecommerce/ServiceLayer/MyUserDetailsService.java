package com.example.backend_ecommerce.ServiceLayer;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MyUserDetailsService implements UserDetailsService{
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
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
