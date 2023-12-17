package com.anything.codeanything.modules.user.model;
public class UserAccountDetails {
    private long user_id;
    private String email;
    private String username;
    private String login_id;
    private String raw_password;
    private String old_password;

    public UserAccountDetails(long user_id, String email, String username, String login_id, String raw_password, String old_password){
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.login_id = login_id;
        this.raw_password = raw_password;
        this.old_password = old_password;
    }
    public void setUserId(long pUserId){this.user_id = pUserId;}
    public long getUserId(){
        return this.user_id;
    }

    public void setEmail(String pEmail){this.email = pEmail;}
    public String getEmail(){
        return this.email;
    }

    public void setUsername(String pUsername){this.username = pUsername;}

    public String getUsername(){
        return this.username;
    }

    public void setLoginId(String pLoginId){this.login_id = pLoginId;}
    public String getLoginId(){
        return this.login_id;
    }

    public void setRawPassword(String pRawPassword){this.raw_password = pRawPassword;}
    public String getRawPassword(){
        return this.raw_password;
    }

    public void setOldPassword(String pOldPassword){this.old_password = pOldPassword;}
    public String getOldPassword(){
        return this.old_password;
    }
}
