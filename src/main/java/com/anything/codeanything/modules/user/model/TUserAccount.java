package com.anything.codeanything.modules.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "T_USER_ACCOUNT")
public class TUserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;
    private String username;
    private String email;
    private String password_salt;
    private String password_hash;
    private Instant created_date;
    private Instant updated_date;

    public TUserAccount(String pUsername, String pEmail, String pPasswordSalt,String pPasswordHash,Instant pCreatedDate,Instant pUpdatedDate){
        this.username = pUsername;
        this.email = pEmail;
        this.password_salt = pPasswordSalt;
        this.password_hash = pPasswordHash;
        this.created_date = pCreatedDate;
        this.updated_date = pUpdatedDate;
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
