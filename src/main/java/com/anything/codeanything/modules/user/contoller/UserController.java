package com.anything.codeanything.modules.user.contoller;

import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccountDetails;
import com.anything.codeanything.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
      this.userService = userService;
    }

    @PostMapping("/user-sign-up")
    public ResponseEntity<TUserAccount> UserSignUp(@RequestBody UserAccountDetails pUserAccountDetails){
            TUserAccount userAccount = userService.userSignUp(pUserAccountDetails);
        //return new ResponseEntity<TUserAccount>("Product created successfully", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.OK).body(userAccount);
    }

    @PostMapping("/login-user")
    public ResponseEntity<Boolean> LoginUser(@RequestBody UserAccountDetails pUserAccountDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(pUserAccountDetails.getLoginId(), pUserAccountDetails.getRawPassword()));
    }

    @GetMapping("/get-user-account-list")
    public List<TUserAccount> getUserAccountList() {
        return userService.getUserAccountList();
    }

    @GetMapping("/test-get-user-account-list")
    public ResponseEntity<List<TUserAccount>> getUserAccountListv2() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserAccountList());
    }
}
