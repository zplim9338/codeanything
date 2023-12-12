package com.anything.codeanything.modules.user.service;

import java.util.List;
import com.anything.codeanything.modules.user.model.TUserAccount;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    // TUserAccount createUserAccount(TUserAccount pUserAccount);
    List<TUserAccount> getUserAccountList();
    String getUserAccountTest();
    //Other methods as needed
}
