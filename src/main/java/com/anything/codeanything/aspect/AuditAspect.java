package com.anything.codeanything.aspect;

import com.anything.codeanything.model.TAuditTrail;
import com.anything.codeanything.service.AuditTrailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.UUID;

@Aspect
@Component
public class AuditAspect {

    private final AuditTrailService auditTrailService;
    private final ObjectMapper objectMapper;

    public AuditAspect(AuditTrailService auditTrailService, ObjectMapper objectMapper) {
        this.auditTrailService = auditTrailService;
        this.objectMapper = objectMapper;
    }

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Get HTTP request details
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Capture request information
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        String requestBody = ""; //request.getReader().lines().collect(Collectors.joining());
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        Object result = null;
        Integer statusCode = 200;
        String responseBody = null;

        try {
            // Execute the actual method
            result = joinPoint.proceed();
            responseBody = objectMapper.writeValueAsString(result);
            return result;
        } catch (Exception e) {
            statusCode = 500;
            responseBody = e.getMessage();
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            // Create and save audit trail
            TAuditTrail auditTrail = TAuditTrail.builder()
                    .uuid(UUID.randomUUID().toString())
                    .endpoint(endpoint)
                    .method(method)
                    .requestParams(queryString)
                    .requestBody(requestBody)
                    .responseBody(responseBody)
                    .statusCode(statusCode)
                    .executionTime(executionTime)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .createdAt(LocalDateTime.now())
                    .build();

            auditTrailService.insertAuditTrail(auditTrail);
        }
    }
}