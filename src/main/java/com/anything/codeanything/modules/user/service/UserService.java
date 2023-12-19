package com.anything.codeanything.modules.user.service;

import com.anything.codeanything.modules.user.model.ApiRequest;
import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    TUserAccount userSignUp(UserAccountDetails userAccountDetails);
    void loginUser(ApiRequest<UserAccountDetails, TUserAccount> request);
    boolean changeUserAccountPassword(UserAccountDetails userAccountDetails, Optional<Boolean> pOptionalCheckCurrentPassword);
    List<TUserAccount> getUserAccountList();
    //Other methods as needed
}
