package com.anything.codeanything.modules.user.service.impl;

import com.anything.codeanything.modules.user.model.TUserAccount;
// import com.codeanything.modules.user.repository.UserRepository;
import com.anything.codeanything.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    // private final UserRepository userRepository;
    // @Autowired
    // public UserServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    // @Override
    // public TUserAccount createUserAccount(TUserAccount product) {
    //     String passwordHash = this.generatePasswordHash();
    //     return userRepository.save(product);
    // }

    // @Override
    // public List<TUserAccount> getUserAccountList() {
    //     return userRepository.findAll();
    // }
    @Override
    public String getUserAccountTest() {
        return "TESTACCOUNTPAGE";
    }

    private String generatePasswordHash(){
        return "ABC";
    }
    private String generatePasswordSalt(){
        return "ABC123";
    }
}
