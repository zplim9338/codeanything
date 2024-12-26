package com.anything.codeanything.contoller;

import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.TFiles;
import com.anything.codeanything.model.TProfile;
import com.anything.codeanything.service.AboutMeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/aboutme")
@RestController
@Slf4j
public class AboutMeController {
    private final String mModule = "aboutme";
    private final AboutMeService aboutMeService;

    @Autowired
    public AboutMeController(AboutMeService aboutMeService) {
        this.aboutMeService = aboutMeService;
    }
    @PostMapping("/get-aboutme")
    public ResponseEntity<ApiResponse<String>> GetAboutMe(@RequestBody String pUserId) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            String jsonResult = this.aboutMeService.getAboutMe(pUserId);
            response = ApiResponse.<String>builder()
                    .status(true)
                    .message("")
                    .data(jsonResult).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<String>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data("").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get-tenant-info")
    public ResponseEntity<ApiResponse<String>> GetTenantInfo(@RequestBody String pTenantId) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            String jsonResult = this.aboutMeService.getAboutMe(pTenantId);
            response = ApiResponse.<String>builder()
                    .status(true)
                    .message("")
                    .data(jsonResult).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<String>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data("").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send-telegram-message")
    public ResponseEntity<ApiResponse<String>> SendTgMsg(@RequestBody String pText) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            this.aboutMeService.sendTelegramMessage(response, pText);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<String>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(null).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("type") String type) {
        return aboutMeService.uploadFile(file, type);
    }

    @GetMapping("/get-profile/{slug}")
    public ResponseEntity<ApiResponse<TProfile>> getProfileBySlug(@PathVariable String slug) {
        ApiResponse<TProfile> response = new ApiResponse<>();
        try {
            TProfile rst = this.aboutMeService.getProfileBySlug(slug);
            response = ApiResponse.<TProfile>builder()
                    .status(true)
                    .message("")
                    .data(rst).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<TProfile>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(null).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download-resume/{slug}")
    public ResponseEntity<ByteArrayResource> downloadResume(@PathVariable String slug) {
        TFiles resume = aboutMeService.getProfileResume(slug);

        ByteArrayResource resource = new ByteArrayResource(resume.getFileContent());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(resume.getMimeType()))
                .contentLength(resume.getFileSize())
                .body(resource);
    }
}
