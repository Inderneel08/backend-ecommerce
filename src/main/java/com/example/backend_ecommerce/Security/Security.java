package com.example.backend_ecommerce.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backend_ecommerce.Components.JwtFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
public class Security {

        @Autowired
        private JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/login", "/api/auth/register",
                                                                "/api/getStates","/api/auth/products/", "/api/auth/products/**",
                                                                 "/api/auth/getAll/categories","/img/**","/api/auth/products/category/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .formLogin(form -> form.disable());

                httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return (httpSecurity.build());
        }


        @Bean
        public WebMvcConfigurer corsConfigurer()
        {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(CorsRegistry registry) {
                                registry.addMapping("/api/auth/**")
                                        .allowedOrigins("http://localhost:8080")
                                        .allowedMethods("GET","POST")
                                        .allowedHeaders("*")
                                        .allowCredentials(true);
                        }
                };
        }

}
