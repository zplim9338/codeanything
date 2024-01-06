package com.anything.codeanything.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtTokenProvider {
    String getUsernameFromToken(String token);
    Boolean validateToken(String token);
    //Other methods as needed
    String generateAccessToken(String username); // generate a JWT access token
    String generateRefreshToken(String username); // generate a JWT refresh token
    String refreshAccessToken(String refreshToken); // validate and refresh access token using refresh token
}
