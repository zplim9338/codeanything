package com.anything.codeanything.modules.user.contoller;

import com.anything.codeanything.modules.user.model.TUserAccount;
import com.anything.codeanything.modules.user.model.UserAccount;
import com.anything.codeanything.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<TUserAccount> UserSignUp(@RequestBody UserAccount pUserAccount){
        TUserAccount userAccount = userService.userSignUp(pUserAccount.getTUserAccount(), pUserAccount.getRawPassword());
        //return new ResponseEntity<TUserAccount>("Product created successfully", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.OK).body(userAccount);
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
