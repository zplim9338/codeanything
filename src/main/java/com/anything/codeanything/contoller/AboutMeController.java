package com.anything.codeanything.contoller;

import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.service.AboutMeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
