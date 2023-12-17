package com.anything.codeanything.modules.user.service;

import java.util.List;
import java.util.Optional;

import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    TUserAccount userSignUp(UserAccountDetails userAccountDetails);
    boolean loginUser(String usernameEmail, String rawPassword);
    boolean changeUserAccountPassword(UserAccountDetails userAccountDetails, Optional<Boolean> pOptionalCheckCurrentPassword);
    List<TUserAccount> getUserAccountList();
    //Other methods as needed
}
