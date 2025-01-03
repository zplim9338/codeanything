package com.anything.codeanything.service.impl;

import com.anything.codeanything.mapper.aboutme.AboutMeMapper;
import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.TFiles;
import com.anything.codeanything.model.TelegramSendMsgBody;
import com.anything.codeanything.model.TProfile;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

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

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/jpg", "image/png"};
    private static final String[] ALLOWED_RESUME_TYPES = {"application/pdf"};

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

    @Override
    public String uploadFile(MultipartFile file, String type) {
        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds 5MB limit");
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("Invalid file type");
        }

        if ("image".equals(type)) {
            if (!Arrays.asList(ALLOWED_IMAGE_TYPES).contains(contentType)) {
                throw new IllegalArgumentException("Invalid image file type");
            }
        } else if ("resume".equals(type)) {
            if (!Arrays.asList(ALLOWED_RESUME_TYPES).contains(contentType)) {
                throw new IllegalArgumentException("Invalid resume file type");
            }
        } else {
            throw new IllegalArgumentException("Invalid file type category");
        }

        try {
            // Generate UUID and get file information
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            
            // Create and save file record in database
            TFiles fileRecord = TFiles.builder()
                .uuid(uuid)
                .fileName(originalFilename)
                .fileType(type)
                .fileExtension(fileExtension.substring(1)) // Remove the dot
                .fileSize(file.getSize())
                .mimeType(contentType)
                .fileContent(file.getBytes()) // Store the actual file content
                .uploadDate(LocalDateTime.now())
                .build();

            aboutMeMapper.insertFile(fileRecord);

            // Return UUID for reference
            return fileRecord.getUuid();

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public TFiles getFile(String uuid) {
        return aboutMeMapper.getFileByUuid(uuid);
    }

    @Override
    public TProfile getProfileBySlug(String slug) {
        return aboutMeMapper.getProfileBySlug(slug);
    }

    @Override
    public TFiles getProfileResume(String slug) {
        TFiles resume = aboutMeMapper.getProfileResume(slug);
        if (resume == null) {
            throw new RuntimeException("Resume not found for profile: " + slug);
        }
        return resume;
    }

}
