package com.codeanything.modules.user.model;
import java.sql.Timestamp;
//import javax.persistence.Entity;

//@Entity
public class TUserAccount {
    private Integer user_id;
    private String username;
    private String email;
    private String password_salt;
    private String password_hash;
    private Timestamp created_date;
    private Timestamp updated_data;
}
