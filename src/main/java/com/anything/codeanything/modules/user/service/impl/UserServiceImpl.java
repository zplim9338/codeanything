package com.anything.codeanything.modules.user.service.impl;

import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.repository.UserRepository;
import com.anything.codeanything.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;


import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private Instant CurrentUTC = Instant.now();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TUserAccount userSignUp(TUserAccount pUserAccount, String pPlaintextPassword) {
        String passwordHash = this.generatePasswordHash(pPlaintextPassword);
        String passwordSalt = this.generatePasswordSalt();
        TUserAccount userAccount = new TUserAccount(pUserAccount.getUsername(), pUserAccount.getEmail(), passwordHash, passwordSalt, CurrentUTC, null);

        return userRepository.save(userAccount);
    }

    @Override
    public List<TUserAccount> getUserAccountList() {
        return userRepository.findAll();
    }

    private String generatePasswordHash(String pPlainTextPassword){
        return "ABCHASH";
    }

    private String generatePasswordSalt(){
        return "ABCSALT";
    }
}
