package com.anything.codeanything.modules.user.service;

import com.anything.codeanything.modules.user.model.ApiRequest;
import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void userSignUp(ApiRequest<UserAccountDetails, TUserAccount> request);
    void loginUser(ApiRequest<UserAccountDetails, TUserAccount> request);
    void changeUserAccountPassword(ApiRequest<UserAccountDetails, Boolean> request);
    void getUserAccountList(ApiRequest<Object, List<TUserAccount>> request);
    //Other methods as needed
}
