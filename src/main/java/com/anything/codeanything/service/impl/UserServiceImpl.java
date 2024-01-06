package com.anything.codeanything.service.impl;

import com.anything.codeanything.enums.UserStatusEnum;
import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.TUserAccount;
import com.anything.codeanything.model.UserAccountDetails;
import com.anything.codeanything.repository.UserRepository;
import com.anything.codeanything.service.UserService;
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
    public void userSignUp(ApiResponse<TUserAccount> refRequest, UserAccountDetails pUserAccountDetails) {
        Boolean status = true;
        String message = "";
        TUserAccount result = new TUserAccount();

        //Validation
        status = userRepository.findByUsernameEquals(pUserAccountDetails.getUsername()).isEmpty();
        if(!status){ message = "Username has been taken."; }

        if(status){
            status = userRepository.findUserByEmail(pUserAccountDetails.getEmail()).isEmpty();
            if(!status){ message = "Email has been registered."; }
        }

        //Save
        if(status){
            String passwordSalt = this.generatePasswordSalt();
            String passwordHash = this.generatePasswordHash(pUserAccountDetails.getRaw_password(), passwordSalt);
            TUserAccount userAccount = TUserAccount.builder()
                    .username(pUserAccountDetails.getUsername())
                    .email(pUserAccountDetails.getEmail())
                    .force_change_password(false)
                    .account_status(UserStatusEnum.PENDING.getCode())
                    .password_salt(passwordSalt)
                    .password_hash(passwordHash)
                    .created_date(CurrentUTC).build();

            result = userRepository.save(userAccount);
            message = "Sign Up Successfully";
        }

        //RETURN DATA
        refRequest.setStatus(status);
        refRequest.setMessage(message);
        refRequest.setData(result);
    }

    @Override
    public void getUserAccountList(ApiResponse<List<TUserAccount>> refRequest) {
        List<TUserAccount> result = userRepository.findAll();

        //RETURN DATA
        refRequest.setStatus(true);
        refRequest.setMessage(String.valueOf(result.size()) + " User Account(s).");
        refRequest.setData(result);
    }

    @Override
    public void loginUser(ApiResponse<TUserAccount> refRequest, UserAccountDetails pUserAccountDetails){
        Boolean status = true;
        String message = "Login Successfully";
        String loginId = pUserAccountDetails.getLogin_id();
        String rawPassword = pUserAccountDetails.getRaw_password();

        TUserAccount userAccount = userRepository.findUserByEmail(loginId).orElse(null);
        if (userAccount == null) {
            userAccount = userRepository.findByUsernameEquals(loginId).orElse(null);
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
        refRequest.setStatus(status);
        refRequest.setMessage(message);
        refRequest.setData(status?userAccount:new TUserAccount());
    }

    @Override
    public void changeUserAccountPassword(ApiResponse<Boolean> request, UserAccountDetails pUserAccountDetails, Boolean pCheckCurrentPassword){
        Boolean status = true;
        String message = "Password Changed Successfully";
        TUserAccount userAccount = null;

        if (status){
            userAccount = userRepository.findById(pUserAccountDetails.getUser_id()).orElse(null);
            status = userAccount != null;
            if(!status) {message = "User account not found.";}
        }

        if (status && pCheckCurrentPassword){
            status = this.validateUserPassword(userAccount, pUserAccountDetails.getOld_password());
            if(!status) {message = "Current password is mismatched.";}
        }

        if (status && pCheckCurrentPassword){
            status = !this.validateUserPassword(userAccount, pUserAccountDetails.getRaw_password());
            if(!status) {message = "New password cannot be same with your current password";}
        }

        if (status){
            String passwordSalt = this.generatePasswordSalt();
            String passwordHash = this.generatePasswordHash(pUserAccountDetails.getRaw_password(), passwordSalt);
            userAccount.setPassword_salt(passwordSalt);
            userAccount.setPassword_hash(passwordHash);
            userRepository.save(userAccount);
        }

        //RETURN DATA
        request.setStatus(status);
        request.setMessage(message);
        request.setData(status);
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

    public TUserAccount updateTUserAccount(TUserAccount pUserAccount){
        return userRepository.save(pUserAccount);
    }

    @Override
    public UserAccountDetails mapTUserAccountToUserAccountDetails(TUserAccount pUserAccount) {
        return UserAccountDetails.builder()
                .user_id(pUserAccount.getUser_id())
                .email(pUserAccount.getEmail())
                .username(pUserAccount.getUsername())
                .token(pUserAccount.getToken())
                .force_change_password(pUserAccount.getForce_change_password())
                .build();
    }

    @Override
    public TUserAccount mapUserAccountDetailsTUserAccount(UserAccountDetails pUserAccountDetails) {
        return TUserAccount.builder()
                .user_id(pUserAccountDetails.getUser_id())
                .username(pUserAccountDetails.getUsername())
                .email(pUserAccountDetails.getEmail())
                .token(pUserAccountDetails.getToken())
                .account_status(pUserAccountDetails.getAccount_status())
                .build();
    }
}
