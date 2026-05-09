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
    // @Value("${jwt.secret}")
    private String serectKey="/u6vP2o2Oyb9y6+kv36cTBDbl1WYd8lAYfLeZ9Zijmrll6nTiGLCNo8vjeErWxB1ixcE4kUHPBtwdN58hFE0BQ==";
   
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

