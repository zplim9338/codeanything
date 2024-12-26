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
public class TFiles {
    private String uuid;
    private String fileName;
    private String fileType;
    private String fileExtension;
    private Long fileSize;
    private String filePath;
    private String mimeType;
    private byte[] fileContent;
    private LocalDateTime uploadDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 