package com.anything.codeanything.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private long user_id;
    private String old_password;
    private String new_password;
    private String confirm_password;
}
