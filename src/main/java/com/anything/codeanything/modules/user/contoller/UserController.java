package com.anything.codeanything.modules.user.contoller;

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

    @PostMapping("/login-user")
    public ResponseEntity<ApiResponse<TUserAccount>> LoginUser(@RequestBody UserAccountDetails pUserAccountDetails) {
        ApiResponse<TUserAccount> response = new ApiResponse<>();

        try{
            this.userService.loginUser(response, pUserAccountDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            response = ApiResponse.<TUserAccount>builder()
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
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            response = ApiResponse.<List<TUserAccount>>builder()
                    .status(false)
                    .message(ex.getMessage())
                    .data(new ArrayList<>()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
