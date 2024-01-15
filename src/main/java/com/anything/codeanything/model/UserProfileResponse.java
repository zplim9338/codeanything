package com.anything.codeanything.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String email;
    private String username;
    private String first_name;
    private String last_name;
    private String nickname;
    private Integer gender;
    private Integer age;
    private Integer phone_no;
    private Integer account_status;
}
