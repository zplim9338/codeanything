package com.anything.codeanything.service;

import com.anything.codeanything.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void registerUserValidation(ApiResponse<TUserAccount> response, RegisterUserRequest registerUserRequest);
    void registerUserSave(ApiResponse<TUserAccount> response, RegisterUserRequest registerUserRequest);
    void loginUserValidation(ApiResponse<TUserAccount> response, LoginUserRequest loginUserRequest);
    void changeUserAccountPassword(ApiResponse<Boolean> response, ChangePasswordRequest changePasswordRequest, Boolean checkCurrentPassword);
    void getUserAccountList(ApiResponse<List<TUserAccount>> response);
    UserProfileResponse getUserProfileById(long user_id);
    TUserAccount updateTUserAccount(TUserAccount userAccount);
    //Other methods as needed
}
