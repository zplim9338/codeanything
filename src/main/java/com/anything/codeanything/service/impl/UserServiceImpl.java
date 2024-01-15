package com.anything.codeanything.service.impl;

import com.anything.codeanything.enums.UserStatusEnum;
import com.anything.codeanything.mapper.UserMapper;
import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.ChangePasswordRequest;
import com.anything.codeanything.model.LoginUserRequest;
import com.anything.codeanything.model.RegisterUserRequest;
import com.anything.codeanything.model.TUserAccount;
import com.anything.codeanything.model.UserProfileResponse;
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
    private final UserMapper userMapper;
    private Instant CurrentUTC = Instant.now();

    @Autowired
    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void registerUserValidation(ApiResponse<TUserAccount> refRequest, RegisterUserRequest pRegisterUserRequest) {
        Boolean status = true;
        String message = "";

        //Validation
        status = userRepository.findByUsernameEquals(pRegisterUserRequest.getUsername()).isEmpty();
        if(!status){ message = "Username has been taken."; }

        if(status){
            status = userRepository.findUserByEmail(pRegisterUserRequest.getEmail()).isEmpty();
            if(!status){ message = "Email has been registered."; }
        }

        if(status){
            status = !pRegisterUserRequest.getRaw_password().equals(pRegisterUserRequest.getConfirm_password());
            if(!status){ message = "Your password is mismatched."; }
        }

        //RETURN DATA
        refRequest.setStatus(status);
        refRequest.setMessage(message);
        refRequest.setData(new TUserAccount());
    }

    @Override
    public void registerUserSave(ApiResponse<TUserAccount> refRequest, RegisterUserRequest pRegisterUserRequest) {
        String passwordSalt = this.generatePasswordSalt();
        String passwordHash = this.generatePasswordHash(pRegisterUserRequest.getRaw_password(), passwordSalt);
        TUserAccount userAccount = TUserAccount.builder()
                .username(pRegisterUserRequest.getUsername())
                .email(pRegisterUserRequest.getEmail())
                .force_change_password(false)
                .account_status(UserStatusEnum.PENDING.getCode())
                .password_salt(passwordSalt)
                .password_hash(passwordHash)
                .created_date(CurrentUTC).build();

        TUserAccount result = userRepository.save(userAccount);

        refRequest.setStatus(true);
        refRequest.setMessage("Sign Up Successfully");
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
    public UserProfileResponse getUserProfileById(long user_id) {
        return userMapper.getUserProfileById(user_id);
    }

    @Override
    public void loginUserValidation(ApiResponse<TUserAccount> refRequest, LoginUserRequest pLoginUserRequest){
        Boolean status = true;
        String message = "";
        String loginId = pLoginUserRequest.getLogin_id();
        String password = pLoginUserRequest.getPassword();

        TUserAccount userAccount = userRepository.findUserByEmail(loginId).orElse(null);
        if (userAccount == null) {
            userAccount = userRepository.findByUsernameEquals(loginId).orElse(null);
        }
        if (userAccount == null) {
            status = false;
            message = "Account/Password is mismatched.";
        }
        if (status) {
            status = this.validateUserPassword(userAccount, password);
            if (!status) {message = "Account/Password is mismatched.";}
        }
        if (status) {
            message = "Login Successfully";
        }

        //RETURN DATA
        refRequest.setStatus(status);
        refRequest.setMessage(message);
        refRequest.setData(userAccount);
    }

    @Override
    public void changeUserAccountPassword(ApiResponse<Boolean> request, ChangePasswordRequest pChangePasswordRequest, Boolean pCheckCurrentPassword){
        Boolean status = true;
        String message = "Password Changed Successfully";
        TUserAccount userAccount = null;

        if (status){
            userAccount = userRepository.findById(pChangePasswordRequest.getUser_id()).orElse(null);
            status = userAccount != null;
            if(!status) {message = "User account not found.";}
        }

        if (status && pCheckCurrentPassword){
            status = this.validateUserPassword(userAccount, pChangePasswordRequest.getOld_password());
            if(!status) {message = "Current password is mismatched.";}
        }

        if (status && pCheckCurrentPassword){
            status = !this.validateUserPassword(userAccount, pChangePasswordRequest.getNew_password());
            if(!status) {message = "New password cannot be same with your current password";}
        }

        if (status){
            String passwordSalt = this.generatePasswordSalt();
            String passwordHash = this.generatePasswordHash(pChangePasswordRequest.getNew_password(), passwordSalt);
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
}
