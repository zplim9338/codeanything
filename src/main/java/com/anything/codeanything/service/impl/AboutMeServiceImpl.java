package com.anything.codeanything.service.impl;

import com.anything.codeanything.mapper.aboutme.AboutMeMapper;
import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.TelegramSendMsgBody;
import com.anything.codeanything.service.AboutMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AboutMeServiceImpl  implements AboutMeService {
    private final AboutMeMapper aboutMeMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public AboutMeServiceImpl(@Qualifier("aboutMeMapper") AboutMeMapper aboutMeMapper) {
        this.aboutMeMapper = aboutMeMapper;
    }

    @Value("${telegram.bot_token}")
    private String TELEGRAM_BOT_TOKEN;

    @Value("${telegram.chat_id}")
    private long TELEGRAM_CHAT_ID;

    @Override
    public String getAboutMe(String user_id) {
        String jsonData = aboutMeMapper.getAboutMe(user_id);
        return jsonData;
    }
    @Override
    public void sendTelegramMessage(ApiResponse<String> refRequest, String text) {
        String TELEGRAM_API_URL = "https://api.telegram.org/bot" + TELEGRAM_BOT_TOKEN + "/sendMessage";
//        try {
        // Set up headers (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create TelegramSendMsgBody instance
        TelegramSendMsgBody messageBody = TelegramSendMsgBody.builder()
                .chat_id(TELEGRAM_CHAT_ID)
                .text(text)
                .build();

        // Create HttpEntity with headers and body
        HttpEntity<TelegramSendMsgBody> entity = new HttpEntity<>(messageBody, headers);
        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(TELEGRAM_API_URL, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            refRequest.setStatus(true);
            refRequest.setMessage("Message sent successfully: " + response.getBody());
//            System.out.println("Message sent successfully: " + response.getBody());
        } else {
            refRequest.setStatus(false);
            refRequest.setMessage("Failed to send message: " + response.getStatusCode());
//            System.err.println("Failed to send message: " + response.getStatusCode());
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
