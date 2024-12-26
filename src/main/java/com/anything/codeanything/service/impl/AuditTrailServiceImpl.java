package com.anything.codeanything.service.impl;

import com.anything.codeanything.mapper.aboutme.AboutMeMapper;
import com.anything.codeanything.model.TAuditTrail;
import com.anything.codeanything.service.AuditTrailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditTrailServiceImpl implements AuditTrailService {

    private final AboutMeMapper aboutMeMapper;

    public AuditTrailServiceImpl(AboutMeMapper aboutMeMapper) {
        this.aboutMeMapper = aboutMeMapper;
    }

    @Override
    @Transactional
    public void insertAuditTrail(TAuditTrail auditTrail) {
        aboutMeMapper.insertAuditTrail(auditTrail);
    }
} 