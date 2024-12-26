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
public class TProfile {
    private String uuid;
    private String slug;
    private String nickname;
    private String title;
    private String profilePicUrl;
    private String profilePicFileUuid;
    private String resumeFileUuid;
    private String introduction;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String githubId;
    private String linkedinId;
    private String whatsappId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Join with T_FILES
    private TFiles profilePicFile;
} 