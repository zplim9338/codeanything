package com.anything.codeanything.contoller;

import com.anything.codeanything.model.ApiResponse;
import com.anything.codeanything.model.TUserAccount;
import com.anything.codeanything.model.UserAccountDetails;
import com.anything.codeanything.service.JwtTokenProvider;
import com.anything.codeanything.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    private final String mModule = "user";
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
      this.userService = userService;
      this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
     * For successful responses, status could hold 200 (OK), 201 (Created), or other relevant success status codes.
     * For errors, it could contain status codes such as 400 (Bad Request), 404 (Not Found), 500 (Internal Server Error), etc., indicating the nature of the error.
     * */

    @PostMapping("/user-sign-up")
    public ResponseEntity<ApiResponse<TUserAccount>> UserSignUp(@RequestBody UserAccountDetails pUserAccountDetails){
        ApiResponse<TUserAccount> response = new ApiResponse<>();

        try{
            this.userService.userSignUp(response, pUserAccountDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response = ApiResponse.<TUserAccount>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(new TUserAccount()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/is-authenticated")
    public ResponseEntity<ApiResponse<String>> TestAuthenticated(@RequestHeader("Authorization") String token, @RequestBody UserAccountDetails pUserAccountDetails) {
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


    @PostMapping("/login-user")
    public ResponseEntity<ApiResponse<UserAccountDetails>> LoginUser(@RequestBody UserAccountDetails pUserAccountDetails) {
        ApiResponse<UserAccountDetails> response = new ApiResponse<>();

        try{
            ApiResponse<TUserAccount> loginUserResp = new ApiResponse<>();

            //GET USER
            this.userService.loginUser(loginUserResp, pUserAccountDetails);
            response.setStatus(loginUserResp.getStatus());
            response.setMessage(loginUserResp.getMessage());
            response.setData(this.userService.mapTUserAccountToUserAccountDetails(loginUserResp.getData()));

            //GET & UPDATE TOKEN
            if(response.getStatus()) {
                String refreshToken = this.jwtTokenProvider.generateRefreshToken(response.getData().getUsername());
                String accessToken = this.jwtTokenProvider.generateAccessToken(response.getData().getUsername());
                UserAccountDetails userAccountDetails = response.getData();
                userAccountDetails.setToken(refreshToken);
                userAccountDetails.setAccess_token(accessToken);
                loginUserResp.getData().setToken(refreshToken);
                this.userService.updateTUserAccount(loginUserResp.getData());
                response.setData(userAccountDetails);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            response = ApiResponse.<UserAccountDetails>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(null).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-user-account-password")
    public ResponseEntity<ApiResponse<Boolean>> ChangeUserAccountPassword(@RequestBody UserAccountDetails pUserAccountDetails) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            this.userService.changeUserAccountPassword(response, pUserAccountDetails,true);
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
}
