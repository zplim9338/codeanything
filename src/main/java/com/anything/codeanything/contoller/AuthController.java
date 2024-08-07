package com.anything.codeanything.contoller;

import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.AuthResponse;
import com.anything.codeanything.service.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshAuthenticationToken(@RequestBody Map<String, String> request) throws Exception {
        ApiResponse<AuthResponse> response = new ApiResponse<>();

        try {
            String refreshToken = request.get("refresh_token");
            Boolean status = true;
            String message = "";

            status = jwtTokenProvider.validateToken(refreshToken);
            if(!status) {message = "Invalid Refresh Token";}

            if(status){
                long userId = jwtTokenProvider.extractUserId(refreshToken);
                AuthResponse data = new AuthResponse();
                data.setRefreshToken(jwtTokenProvider.generateRefreshToken(userId));
                data.setAccessToken(jwtTokenProvider.generateAccessToken(userId));
                response = ApiResponse.<AuthResponse>builder()
                        .status(true)
                        .message("Authentication token refresh succeeded!")
                        .data(data).build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response = ApiResponse.<AuthResponse>builder()
                        .status(false)
                        .message(message)
                        .data(null).build();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex) {
            response = ApiResponse.<AuthResponse>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(null).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
