package com.anything.codeanything.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_USER_ACCOUNT")
public class TUserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;
    private String username;
    private String email;
    private String password_salt;
    private String password_hash;
    private String token;
    private Boolean force_change_password;
    private Integer account_status;
    private Instant created_date;
    private Instant updated_date;
}
