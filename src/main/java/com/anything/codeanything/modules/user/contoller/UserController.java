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

import java.util.List;
import java.util.Optional;

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
        TUserAccount userAccount = userService.userSignUp(pUserAccountDetails);

        //return new ResponseEntity<TUserAccount>("Product created successfully", HttpStatus.CREATED);
        HttpStatus httpStatus = HttpStatus.OK;
        ApiResponse<TUserAccount> response = new ApiResponse<>();
        response.setStatus(httpStatus.value());
        response.setMessage("User account created successful");
        response.setData(userAccount);
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/login-user")
    public ResponseEntity<ApiResponse<TUserAccount>> LoginUser(@RequestBody UserAccountDetails pUserAccountDetails) {
        ApiRequest<UserAccountDetails, TUserAccount> request = ApiRequest.<UserAccountDetails, TUserAccount>builder()
                .status(true).data(pUserAccountDetails).build();
        try{
            userService.loginUser(request);
            HttpStatus httpStatus = HttpStatus.OK;
            ApiResponse<TUserAccount> response = ApiResponse.<TUserAccount>builder()
                    .status(httpStatus.value())
                    .message(request.getMessage())
                    .data(request.getResult()).build();

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
        Boolean status = userService.changeUserAccountPassword(pUserAccountDetails, Optional.empty());
        HttpStatus httpStatus = HttpStatus.OK;
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setStatus(httpStatus.value());
        response.setMessage("???");
        response.setData(status);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/get-user-account-list")
    public ResponseEntity<ApiResponse<List<TUserAccount>>> getUserAccountList() {
        List<TUserAccount> userAccountList = userService.getUserAccountList();

        HttpStatus httpStatus = HttpStatus.OK;
        ApiResponse<List<TUserAccount>> response = new ApiResponse<>();
        response.setStatus(httpStatus.value());
        response.setMessage("???");
        response.setData(userAccountList);
        return new ResponseEntity<>(response, httpStatus);
    }
}
