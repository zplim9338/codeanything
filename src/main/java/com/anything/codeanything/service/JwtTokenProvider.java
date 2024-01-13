package com.anything.codeanything.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtTokenProvider {
    String getUsernameFromToken(String token);
    Boolean validateToken(String token);
    //Other methods as needed
    String generateAccessToken(long user_id); // generate a JWT access token
    String generateRefreshToken(long user_id); // generate a JWT refresh token
    String refreshAccessToken(String refreshToken); // validate and refresh access token using refresh token
}
