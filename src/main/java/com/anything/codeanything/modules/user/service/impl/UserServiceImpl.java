package com.anything.codeanything.modules.user.service.impl;

import com.anything.codeanything.modules.user.enums.UserStatusEnum;
import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import com.anything.codeanything.modules.user.repository.UserRepository;
import com.anything.codeanything.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private Instant CurrentUTC = Instant.now();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TUserAccount userSignUp(UserAccountDetails pUserAccountDetails) {
        String passwordSalt = this.generatePasswordSalt();
        String passwordHash = this.generatePasswordHash(pUserAccountDetails.getRaw_password(), passwordSalt);
        TUserAccount userAccount = new TUserAccount();
        userAccount = userAccount.builder()
                .username(pUserAccountDetails.getUsername())
                .email(pUserAccountDetails.getEmail())
                .force_change_password(false)
                .account_status(Integer.parseInt(UserStatusEnum.PENDING.getCode()))
                .password_salt(passwordSalt)
                .password_hash(passwordHash)
                .created_date(CurrentUTC).build();
        return userRepository.save(userAccount);
    }

    @Override
    public List<TUserAccount> getUserAccountList() {
        return userRepository.findAll();
    }

    @Override
    public boolean loginUser(String pUsernameEmail, String pRawPassword){
        TUserAccount userAccount = userRepository.findUserByEmail(pUsernameEmail);
        if (userAccount == null){
            userAccount = userRepository.findByUsernameEquals(pUsernameEmail);
        }
        if (userAccount == null){
            return false;
        }

        return this.validateUserPassword(userAccount, pRawPassword);
    }

    @Override
    public boolean changeUserAccountPassword(UserAccountDetails pUserAccountDetails, Optional<Boolean> pOptionalCheckCurrentPassword){
        Boolean checkCurrentPassword = pOptionalCheckCurrentPassword.orElse(false);
        Boolean isValid = true;
        TUserAccount userAccount = null;

        if (isValid){
            userAccount = userRepository.findById(pUserAccountDetails.getUser_id()).orElse(null);
            isValid = userAccount != null;
        }

        if (isValid && checkCurrentPassword){
            isValid = this.validateUserPassword(userAccount,pUserAccountDetails.getOld_password());
        }

        if (isValid){
            String passwordSalt = this.generatePasswordSalt();
            String passwordHash = this.generatePasswordHash(pUserAccountDetails.getRaw_password(), passwordSalt);
            userAccount.setPassword_salt(passwordSalt);
            userAccount.setPassword_hash(passwordHash);
            userRepository.save(userAccount);
        }

        return true;
    }

    private String generatePasswordHash(String pRawPassword, String pPasswordSalt){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pRawPassword + pPasswordSalt);
    }

    private String generatePasswordSalt(){
        int strength = 10; // You can set the strength as needed
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);
        return encoder.encode("");
    }

    private boolean validateUserPassword(TUserAccount pUserAccount, String pRawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordHash = pUserAccount.getPassword_hash();
        String passwordSalt = pUserAccount.getPassword_salt();
        return encoder.matches(pRawPassword + passwordSalt, passwordHash);
    }
}
