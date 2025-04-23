package com.example.backend_ecommerce.ServiceLayer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_ecommerce.Models.TokenTable;
import com.example.backend_ecommerce.RepositoryLayer.TokenRepository;

@Service
public class JwtTokenServiceLayer {
    
    @Autowired
    private TokenRepository tokenRepository;

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

        tokenRepository.save(token);

        return(generatedToken);
    }
}
