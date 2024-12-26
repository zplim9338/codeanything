package com.anything.codeanything.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TAuditTrail {
    private String uuid;
    private String endpoint;
    private String method;
    private String requestParams;
    private String requestBody;
    private String responseBody;
    private Integer statusCode;
    private Long executionTime;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
} 