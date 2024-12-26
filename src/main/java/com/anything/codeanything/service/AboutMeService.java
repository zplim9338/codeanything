package com.anything.codeanything.service;

import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.TProfile;
import com.anything.codeanything.model.TFiles;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AboutMeService {
    String getAboutMe(String user_id);
    void sendTelegramMessage(ApiResponse<String> response, String text);
    String uploadFile(MultipartFile file, String type);
    TProfile getProfileBySlug(String slug);
    TFiles getProfileResume(String slug);
}
