package com.example.backend_ecommerce.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Security {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/login", "/api/auth/register","/api/getStates","/api/products","/api/products/category","/api/getAll/categories").permitAll()
                .anyRequest().authenticated())
                .formLogin().disable();

        return (httpSecurity.build());
    }
}
