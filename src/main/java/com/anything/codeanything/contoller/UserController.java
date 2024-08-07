package com.anything.codeanything.contoller;

import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.ChangePasswordRequest;
import com.anything.codeanything.model.LoginUserRequest;
import com.anything.codeanything.model.LoginUserResponse;
import com.anything.codeanything.model.TUserAccount;
import com.anything.codeanything.model.RegisterUserRequest;
import com.anything.codeanything.model.UserContext;
import com.anything.codeanything.model.UserProfileResponse;
import com.anything.codeanything.service.JwtTokenProvider;
import com.anything.codeanything.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/user")
@RestController
@Slf4j
public class UserController {
    private final String mModule = "user";
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public UserController(JwtTokenProvider jwtTokenProvider, UserService userService, SqlSessionTemplate sqlSessionTemplate) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    /*
     * For successful responses, status could hold 200 (OK), 201 (Created), or other relevant success status codes.
     * For errors, it could contain status codes such as 400 (Bad Request), 404 (Not Found), 500 (Internal Server Error), etc., indicating the nature of the error.
     * */

    @PostMapping("register-user")
    public ResponseEntity<ApiResponse<TUserAccount>> RegisterUser(@RequestBody RegisterUserRequest pRegisterUserRequest){
        ApiResponse<TUserAccount> response = new ApiResponse<>();

        try{
            //Validation
            this.userService.registerUserValidation(response, pRegisterUserRequest);
            if (response.getStatus()){
                //Save
                this.userService.registerUserSave(response, pRegisterUserRequest);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<TUserAccount>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(new TUserAccount()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login-user")
    public ResponseEntity<ApiResponse<LoginUserResponse>> LoginUser(@RequestBody LoginUserRequest pLoginUserRequest) {
        ApiResponse<LoginUserResponse> response = new ApiResponse<>();
        try{
            LoginUserResponse loginUserResponse = new LoginUserResponse();
            //Validation
            ApiResponse<TUserAccount> userAccountValidationResp = new ApiResponse<>();
            this.userService.loginUserValidation(userAccountValidationResp, pLoginUserRequest);

            if(userAccountValidationResp.getStatus()){
                //SET TOKEN
                TUserAccount userAccount = userAccountValidationResp.getData();
                String refreshToken = this.jwtTokenProvider.generateRefreshToken(userAccount.getUser_id());
                String accessToken = this.jwtTokenProvider.generateAccessToken(userAccount.getUser_id());
                userAccount.setToken(refreshToken);
                userAccount = this.userService.updateTUserAccount(userAccount);

                //Build Response
                loginUserResponse = LoginUserResponse.builder()
                        .username(userAccount.getUsername())
                        .email(userAccount.getEmail())
                        .access_token(accessToken)
                        .refresh_token(userAccount.getToken())
                        .force_change_password(userAccount.getForce_change_password())
                        .account_status(userAccount.getAccount_status())
                        .build();

                log.info(String.format("{0} Logged In."), pLoginUserRequest.getLogin_id());
            }

            response.setStatus(userAccountValidationResp.getStatus());
            response.setMessage(userAccountValidationResp.getMessage());
            response.setData(loginUserResponse);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            log.error(ex.getMessage());
            response = ApiResponse.<LoginUserResponse>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(null).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-user-account-password")
    @Transactional
    public ResponseEntity<ApiResponse<Boolean>> ChangeUserAccountPassword(@RequestBody ChangePasswordRequest pChangePasswordRequest, @ModelAttribute("userContext") UserContext pUserContext) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            long user_id = pUserContext.getUser_id();
            pChangePasswordRequest.setUser_id(user_id);
            this.userService.changeUserAccountPassword(sqlSessionTemplate, response, pChangePasswordRequest,true);
            // Manually mark the transaction for rollback
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<Boolean>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(false).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-user-account-list")
    public ResponseEntity<ApiResponse<List<TUserAccount>>> getUserAccountList() {
        ApiResponse<List<TUserAccount>> response = new ApiResponse<>();
        try {
            this.userService.getUserAccountList(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<List<TUserAccount>>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(new ArrayList<>()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-user-profile/{user_id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@PathVariable("user_id") String user_id) {
        ApiResponse<UserProfileResponse> response = new ApiResponse<>();
        try {
            UserProfileResponse result = this.userService.getUserProfileById(Long.parseLong(user_id));
            response = ApiResponse.<UserProfileResponse>builder()
                    .status(true)
                    .message("")
                    .data(result).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<UserProfileResponse>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(null).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/is-authenticated")
    public ResponseEntity<ApiResponse<String>> TestAuthenticated(@RequestHeader("Authorization") String token, @RequestBody UserProfileResponse pUserProfileResponse) {
        ApiResponse<String> response = new ApiResponse<>();
        token = token.split(" ")[1];
        Boolean isValid = this.jwtTokenProvider.validateToken(token);
        if (isValid){
            response = ApiResponse.<String>builder()
                    .status(true)
                    .message("Valid Token")
                    .data("").build();
        }
        else{
            response = ApiResponse.<String>builder()
                    .status(true)
                    .message("Invalid Token")
                    .data("").build();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
