package com.anything.codeanything.config;

import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String username = null;
        String ipAddress = request.getRemoteAddr();

        // Perform operations before the request reaches the controller
        // For example, logging the incoming request details
        System.out.println("Request URL: " + request.getRequestURI());
        System.out.println("HTTP Method: " + request.getMethod());

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String token = authorizationHeader.substring(7);

            try {
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

                username = claims.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                // Handle token exceptions here (expired token, malformed token, etc.)
                ApiResponse<String> errorResponse = ApiResponse.<String>builder()
                        .status(false)
                        .message("Unauthorized: Invalid or expired token")
                        .data(e.getMessage()).build();

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                response.getWriter().write("Unauthorized: Invalid or expired token");

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(response.getWriter(), errorResponse);
                return;
            }
        }
        // Set user id and IP address in UserContext
        UserContext userContext = new UserContext();
        userContext.setUsername(username);
        userContext.setIpAddress(ipAddress);
        // Add UserContext to request attributes
        request.setAttribute("userContext", userContext);

        // Continue the filter chain
        filterChain.doFilter(request, response);

        // Perform operations after the request is processed by the controller
        // For example, logging the response status
        System.out.println("Response Status: " + response.getStatus());
    }
}