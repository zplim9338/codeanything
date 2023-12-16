package com.anything.codeanything.modules.user.service;

import java.util.List;
import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    TUserAccount userSignUp(UserAccountDetails userAccountDetails);
    boolean loginUser(String usernameEmail, String rawPassword);
    List<TUserAccount> getUserAccountList();
    //Other methods as needed
}
