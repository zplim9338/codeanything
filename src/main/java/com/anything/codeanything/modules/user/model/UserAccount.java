package com.anything.codeanything.modules.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccount {
    @JsonProperty("t_user_account")
    private TUserAccount t_user_account;
    @JsonProperty("raw_password")
    private String raw_password;

    public TUserAccount getTUserAccount(){
        return this.t_user_account;
    }
    public String getRawPassword(){
        return this.raw_password;
    }
}
