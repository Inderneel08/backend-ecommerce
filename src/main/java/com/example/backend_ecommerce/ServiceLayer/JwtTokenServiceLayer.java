package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_ecommerce.Models.TokenTable;
import com.example.backend_ecommerce.Models.Users;
import com.example.backend_ecommerce.RepositoryLayer.TokenRepository;
import com.example.backend_ecommerce.RepositoryLayer.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class JwtTokenServiceLayer {
    
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
    }

    public String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes)
                hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public String createToken(BigInteger id)
    {
        String generatedToken = generateRandomString(60);

        String hashedToken = sha256(generatedToken);

        while(tokenRepository.findByTokenValue(hashedToken)!=null){
            generatedToken = generateRandomString(60);

            hashedToken = sha256(generatedToken);
        }

        TokenTable token = new TokenTable();

        token.setToken_value(hashedToken);

        token.setUser_id(id);

        token.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        tokenRepository.save(token);

        return(generatedToken);
    }

    public String findToken(String token)
    {
        String hashedToken = sha256(token);

        return(hashedToken);
    }

    @Transactional
    public boolean deleteToken(String hashedToken)
    {
        try {
            tokenRepository.deleteToken(hashedToken);            
        } catch (Exception e) {
            return(false);
        }

        return(true);
    }

    public String findEmailByToken(String hashedToken)
    {
        TokenTable tokenTable = tokenRepository.findByTokenValue(hashedToken);

        Optional<Users> users = userRepository.findById(tokenTable.getUser_id());

        return(users.get().getEmail());
    }

}
