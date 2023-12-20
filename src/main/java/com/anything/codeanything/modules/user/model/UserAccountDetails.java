package com.anything.codeanything.modules.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDetails {
    private long user_id;
    private String email;
    private String username;
    private String login_id;
    private String raw_password;
    private String old_password;
    private Boolean check_current_password;
}
