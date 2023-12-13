package com.anything.codeanything.modules.user.service;

import java.util.List;
import com.anything.codeanything.modules.user.model.TUserAccount;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    TUserAccount userSignUp(TUserAccount pTUserAccount, String pPlaintextPassword);
    List<TUserAccount> getUserAccountList();
    //Other methods as needed
}
