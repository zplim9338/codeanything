package com.anything.codeanything.service;

import com.anything.codeanything.model.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public interface AboutMeService {
    String getAboutMe(String user_id);
    void sendTelegramMessage(ApiResponse<String> response, String text);
}
