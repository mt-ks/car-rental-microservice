package com.mtks.vehicle.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mtks.vehicle.dto.JwtParseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class JWTAuthVerify {
    private final SecretKey secretKey;


    @Autowired
    public JWTAuthVerify(@Value("${jwt.secret.key}") String base64SecretKey){
        try{
            byte[] decodedKey = Base64.getDecoder().decode(base64SecretKey);
            this.secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
        }catch (Exception e){
            throw new RuntimeException("Failed to generate secret key", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
           throw new AuthenticationCredentialsNotFoundException("Invalid token");
        }
    }

    public String getClaim(String claimName,String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .decryptWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get(claimName).toString();
    }

    public JwtParseDto getJwtData(String token) {
        try{
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .decryptWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(claims,JwtParseDto.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new AuthenticationCredentialsNotFoundException("Invalid token");
        }
    }

}
