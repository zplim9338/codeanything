package com.anything.codeanything.modules.user.service.impl;

import com.anything.codeanything.modules.user.enums.UserStatusEnum;
import com.anything.codeanything.modules.user.model.ApiRequest;
import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import com.anything.codeanything.modules.user.repository.UserRepository;
import com.anything.codeanything.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public void userSignUp(ApiRequest<UserAccountDetails, TUserAccount> request) {
        Boolean status = true;
        String message = "Sign Up Successfully";
        UserAccountDetails data = request.getInput();

        String passwordSalt = this.generatePasswordSalt();
        String passwordHash = this.generatePasswordHash(data.getRaw_password(), passwordSalt);
        TUserAccount userAccount = TUserAccount.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .force_change_password(false)
                .account_status(UserStatusEnum.PENDING.getCode())
                .password_salt(passwordSalt)
                .password_hash(passwordHash)
                .created_date(CurrentUTC).build();

        TUserAccount output = userRepository.save(userAccount);

        //RETURN DATA
        request.setStatus(status);
        request.setMessage(message);
        request.setOutput(output);
    }

    @Override
    public void getUserAccountList(ApiRequest<Object, List<TUserAccount>>request) {
        Boolean status = true;
        String message = "";
        List<TUserAccount> output = userRepository.findAll();
        message = String.valueOf(output.size()) + " User Account(s).";

        //RETURN DATA
        request.setStatus(status);
        request.setMessage(message);
        request.setOutput(output);
    }

    @Override
    public void loginUser(ApiRequest<UserAccountDetails, TUserAccount> request){
        Boolean status = true;
        String message = "Login Successfully";
        UserAccountDetails data = request.getInput();
        String loginId = data.getLogin_id();
        String rawPassword = data.getRaw_password();

        TUserAccount userAccount = userRepository.findUserByEmail(loginId);
        if (userAccount == null) {
            userAccount = userRepository.findByUsernameEquals(loginId);
        }
        if (userAccount == null) {
            status = false;
            message = "Account/Password is mismatched.";
        }
        if (status){
            status = this.validateUserPassword(userAccount, rawPassword);
            if(!status){message = "Account/Password is mismatched.";}
        }

        //RETURN DATA
        request.setStatus(status);
        request.setMessage(message);
        request.setOutput(status?userAccount:new TUserAccount());
    }

    @Override
    public void changeUserAccountPassword(ApiRequest<UserAccountDetails, Boolean> request){
        Boolean status = true;
        String message = "Password Changed Successfully";
        UserAccountDetails data = request.getInput();
        Boolean checkCurrentPassword = data.getCheck_current_password() != null && data.getCheck_current_password();

        TUserAccount userAccount = null;

        if (status){
            userAccount = userRepository.findById(data.getUser_id()).orElse(null);
            status = userAccount != null;
            if(!status) {message = "User account not found.";}
        }

        if (status && checkCurrentPassword){
            status = this.validateUserPassword(userAccount, data.getOld_password());
            if(!status) {message = "Current password is mismatched.";}
        }

        if (status && checkCurrentPassword){
            status = !this.validateUserPassword(userAccount, data.getRaw_password());
            if(!status) {message = "New password cannot be same with your current password";}
        }

        if (status){
            String passwordSalt = this.generatePasswordSalt();
            String passwordHash = this.generatePasswordHash(data.getRaw_password(), passwordSalt);
            userAccount.setPassword_salt(passwordSalt);
            userAccount.setPassword_hash(passwordHash);
            userRepository.save(userAccount);
        }

        //RETURN DATA
        request.setStatus(status);
        request.setMessage(message);
        request.setOutput(status);
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
