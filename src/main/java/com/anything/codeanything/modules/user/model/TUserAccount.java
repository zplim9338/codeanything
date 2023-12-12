package com.anything.codeanything.modules.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_USER_ACCOUNT")
public class TUserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password_salt")
    private String password_salt;
    @JsonProperty("password_hash")
    private String password_hash;
    @JsonProperty("created_date")
    private Timestamp created_date;
    @JsonProperty("updated_date")
    private Timestamp updated_date;

    public TUserAccount(Integer user_id,String username, String email, String password_salt,String password_hash,Timestamp created_date,Timestamp updated_date){
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password_salt = password_salt;
        this.password_hash = password_hash;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }
    public TUserAccount() {
    }
}
