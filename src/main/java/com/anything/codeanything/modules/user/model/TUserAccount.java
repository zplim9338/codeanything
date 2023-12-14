package com.anything.codeanything.modules.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.Instant;

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
    private Instant created_date;
    @JsonProperty("updated_date")
    private Instant updated_date;

    public TUserAccount(String username, String email, String password_salt,String password_hash,Instant created_date,Instant updated_date){
        this.username = username;
        this.email = email;
        this.password_salt = password_salt;
        this.password_hash = password_hash;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public TUserAccount() {
    }

    public Integer getUserId(){
        return this.user_id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String pUsername){
        this.username = pUsername;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String pEmail){
        this.email = pEmail;
    }

    public String getPasswordHash(){
        return this.password_hash;
    }

    public void setPasswordHash(String pPasswordHash){
        this.password_hash = pPasswordHash;
    }

    public String getPasswordSalt(){
        return this.password_salt;
    }

    public void setPasswordSalt(String pPasswordSalt){
        this.password_salt = pPasswordSalt;
    }

}
