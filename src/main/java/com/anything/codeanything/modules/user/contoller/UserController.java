package com.anything.codeanything.modules.user.contoller;

import com.anything.codeanything.modules.user.model.ApiRequest;
import com.anything.codeanything.modules.user.model.ApiResponse;
import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import com.anything.codeanything.modules.user.service.UserService;
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
    @Autowired
    public UserController(UserService userService) {
      this.userService = userService;
    }

    @PostMapping("/user-sign-up")
    public ResponseEntity<ApiResponse<TUserAccount>> UserSignUp(@RequestBody UserAccountDetails pUserAccountDetails){
        ApiRequest<UserAccountDetails, TUserAccount> request = ApiRequest.<UserAccountDetails, TUserAccount>builder()
                .input(pUserAccountDetails).build();

        try{
            this.userService.userSignUp(request);
            HttpStatus httpStatus = HttpStatus.OK;
            ApiResponse<TUserAccount> response = ApiResponse.<TUserAccount>builder()
                    .status(httpStatus.value())
                    .message(request.getMessage())
                    .data(request.getOutput()).build();
            return new ResponseEntity<>(response, httpStatus);
        }catch (Exception ex) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            ApiResponse<TUserAccount> response = ApiResponse.<TUserAccount>builder()
                    .status(httpStatus.value())
                    .message(ex.getMessage())
                    .data(new TUserAccount()).build();
            return new ResponseEntity<>(response, httpStatus);
        }
    }

    @PostMapping("/login-user")
    public ResponseEntity<ApiResponse<TUserAccount>> LoginUser(@RequestBody UserAccountDetails pUserAccountDetails) {
        ApiRequest<UserAccountDetails, TUserAccount> request = ApiRequest.<UserAccountDetails, TUserAccount>builder()
                .input(pUserAccountDetails).build();
        try{
            this.userService.loginUser(request);
            HttpStatus httpStatus = HttpStatus.OK;
            ApiResponse<TUserAccount> response = ApiResponse.<TUserAccount>builder()
                    .status(httpStatus.value())
                    .message(request.getMessage())
                    .data(request.getOutput()).build();

            return new ResponseEntity<>(response, httpStatus);
        }catch (Exception ex){
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            ApiResponse<TUserAccount> response = ApiResponse.<TUserAccount>builder()
                    .status(httpStatus.value())
                    .message(ex.getMessage())
                    .data(null).build();
            return new ResponseEntity<>(response, httpStatus);
        }
    }

    @PostMapping("/change-user-account-password")
    public ResponseEntity<ApiResponse<Boolean>> ChangeUserAccountPassword(@RequestBody UserAccountDetails pUserAccountDetails) {
        ApiRequest<UserAccountDetails, Boolean> request = ApiRequest.<UserAccountDetails, Boolean>builder()
                .input(pUserAccountDetails).build();
        try {
            this.userService.changeUserAccountPassword(request);
            HttpStatus httpStatus = HttpStatus.OK;
            ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                    .status(httpStatus.value())
                    .message(request.getMessage())
                    .data(request.getOutput()).build();
            return new ResponseEntity<>(response, httpStatus);
        }catch (Exception ex) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                    .status(httpStatus.value())
                    .message(ex.getMessage())
                    .data(false).build();
            return new ResponseEntity<>(response, httpStatus);
        }
    }

    @GetMapping("/get-user-account-list")
    public ResponseEntity<ApiResponse<List<TUserAccount>>> getUserAccountList() {
        ApiRequest<Object, List<TUserAccount>> request = new ApiRequest<>();

        try {
            this.userService.getUserAccountList(request);
            HttpStatus httpStatus = HttpStatus.OK;
            ApiResponse<List<TUserAccount>> response = ApiResponse.<List<TUserAccount>>builder()
                    .status(httpStatus.value())
                    .message(request.getMessage())
                    .data(request.getOutput()).build();

            return new ResponseEntity<>(response, httpStatus);
        }catch (Exception ex) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            ApiResponse<List<TUserAccount>> response = ApiResponse.<List<TUserAccount>>builder()
                    .status(httpStatus.value())
                    .message(ex.getMessage())
                    .data(new ArrayList<>()).build();
            return new ResponseEntity<>(response, httpStatus);
        }
    }
}
