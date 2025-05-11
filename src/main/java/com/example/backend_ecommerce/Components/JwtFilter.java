package com.example.backend_ecommerce.Components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend_ecommerce.ServiceLayer.JwtTokenServiceLayer;
import com.example.backend_ecommerce.ServiceLayer.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtTokenServiceLayer jwtTokenServiceLayer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
        String token = null;

        if(request.getCookies()!=null){
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if(token!=null){

            System.out.println("Token : ->".concat(token));

            String hashedToken = jwtTokenServiceLayer.findToken(token);

            System.out.println("HashedToken : ->".concat(hashedToken));

            request.setAttribute("hashedToken", hashedToken);

            String email = jwtTokenServiceLayer.findEmailByToken(hashedToken);

            System.out.println(email);

            if(email!=null){
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
