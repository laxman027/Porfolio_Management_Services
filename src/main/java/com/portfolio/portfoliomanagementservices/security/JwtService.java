package com.portfolio.portfoliomanagementservices.security;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
//	import org.springframework.stereotype.Service;

import com.portfolio.portfoliomanagementservices.model.User;

import java.util.*;

@Component 
public class JwtService {
    //private static final String SECRET_KEY = "portfolio-secret-key";
    
    @Value("${jwt.secret}")
    private String secret_Key;
    
    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(User user) {
    	String response=Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 1 day
                .signWith(SignatureAlgorithm.HS256, secret_Key)
                .compact();
        return response;
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret_Key)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetils) {
        return extractUsername(token).equals(userDetils.getUsername());
    }
}
