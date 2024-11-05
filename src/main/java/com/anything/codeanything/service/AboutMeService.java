package com.anything.codeanything.service;

import org.springframework.stereotype.Service;

@Service
public interface AboutMeService {
    String getAboutMe(String user_id);
}
