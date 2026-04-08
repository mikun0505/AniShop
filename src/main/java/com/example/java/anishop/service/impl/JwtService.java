package com.example.java.anishop.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private String serectKey="quT2qiKK0jNsL/kq/adh1kW+QhnrT2E1zcjJSSBZW70r5P9DFoBIRJiQdQ8oWA81F1u5gcZ758dcJ7db1e8cYg==";

    public String generateToken(String userName){
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSignKey())
                .compact();
    }

    public String generefreshToken(String userName){
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*600*168))
                .signWith(getSignKey())
                .compact();
    }

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    public boolean valiDateToken(String token,UserDetails userDetails){
        String userName=extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token,Function<Claims,T> claisfuc){
        Claims claims=Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload(); 

        return claisfuc.apply(claims);
    }

    private SecretKey getSignKey(){
        byte[] keyByte=Base64.getDecoder().decode(serectKey);
        return Keys.hmacShaKeyFor(keyByte);
    }
}

