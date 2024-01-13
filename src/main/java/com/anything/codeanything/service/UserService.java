package com.anything.codeanything.service;

import com.anything.codeanything.model.ChangePasswordRequest;
import com.anything.codeanything.model.TUserAccount;
import com.anything.codeanything.model.UserAccountDetails;
import com.anything.codeanything.model.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void userSignUp(ApiResponse<TUserAccount> response, UserAccountDetails userAccountDetails);
    void loginUser(ApiResponse<TUserAccount> response, UserAccountDetails userAccountDetails);
    void changeUserAccountPassword(ApiResponse<Boolean> response, ChangePasswordRequest changePasswordRequest, Boolean checkCurrentPassword);
    void getUserAccountList(ApiResponse<List<TUserAccount>> response);
    TUserAccount updateTUserAccount(TUserAccount userAccount);
    UserAccountDetails mapTUserAccountToUserAccountDetails(TUserAccount userAccount);
    TUserAccount mapUserAccountDetailsTUserAccount(UserAccountDetails userAccountDetails);
    //Other methods as needed
}
