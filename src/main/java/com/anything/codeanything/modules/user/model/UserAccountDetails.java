package com.anything.codeanything.modules.user.model;
public class UserAccountDetails {
    private String email;
    private String username;
    private String raw_password;
    private String login_id;

    public UserAccountDetails(String email, String username, String login_id, String raw_password){
        this.email = email;
        this.username = username;
        this.login_id = login_id;
        this.raw_password = raw_password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUsername(){
        return this.username;
    }

    public String getLoginId(){
        return this.login_id;
    }

    public String getRawPassword(){
        return this.raw_password;
    }

}
