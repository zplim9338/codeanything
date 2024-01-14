package com.anything.codeanything.service.impl;

import com.anything.codeanything.repository.UserRepository;
import com.anything.codeanything.service.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final UserRepository userRepository;

    @Autowired
    public JwtTokenProviderImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        String result = (String) claims.get("username");

        return result;
    }

    @Override
    public Boolean validateToken(String token) {
        Boolean result = false;
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            result = !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
        }

        return result;
    }

    // Method to generate a JWT access token
    public String generateAccessToken(long pUserId) {
        return Jwts.builder()
                .claim("user_id", String.valueOf(pUserId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Method to generate a JWT refresh token
    public String generateRefreshToken(long pUserId) {
        return Jwts.builder()
                .claim("user_id", String.valueOf(pUserId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Method to validate and refresh access token using refresh token
    public String refreshAccessToken(String pRefreshToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(pRefreshToken).getBody();
            String user_id = claims.get("user_id", String.class);
            return generateAccessToken(Long.parseLong(user_id));

        } catch (Exception e) {
            // Handle token validation failure
        }
        return null; // Return null if access token refresh fails
    }

    public String getRefreshTokenByUserId(long pUserId){
        return userRepository.findTokenByUserId(pUserId).orElse("");
    }
}
