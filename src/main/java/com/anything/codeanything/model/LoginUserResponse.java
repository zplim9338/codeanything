package com.anything.codeanything.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse {
    private String username;
    private String email;
    private String access_token;
    private String refresh_token;
    private Boolean force_change_password;
    private Integer account_status;
}
