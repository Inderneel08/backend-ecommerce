package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.RepositoryLayer.UserRepository;

import jakarta.transaction.Transactional;

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
        Users users = new Users();

        users.setEmail((String) body.get("email"));

        users.setPassword((String) body.get("password"));

        users.setName((String) body.get("name"));

        users.setRole(0);

        userRepository.save(users);

        return(true);
    }

    @Transactional
    public boolean updatePassword(BigInteger userId,String password)
    {
        try {
            userRepository.savePassword(userId, password);
        } catch (Exception e) {
            return(false);
        }

        return(true);
    }

}
