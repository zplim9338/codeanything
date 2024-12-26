package com.anything.codeanything.service;

import com.anything.codeanything.model.TAuditTrail;
import org.springframework.stereotype.Service;

@Service
public interface AuditTrailService {
    void insertAuditTrail(TAuditTrail auditTrail);
} 