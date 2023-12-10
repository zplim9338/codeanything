package com.codeanything.modules.user.contoller;

import com.codeanything.modules.user.model.TUserAccount;
import com.codeanything.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // @PostMapping("/add")
    // public TUserAccount createUserAccount(@RequestBody TUserAccount product) {
    //     return userService.createUserAccount(product);
    // }
    // @GetMapping("/all")
    // public List<TUserAccount> getUserAccountList() {
    //     return userService.getUserAccountList();
    // }

    @GetMapping("/alltest")
    public String getUserAccountTest() {
        return userService.getUserAccountTest();
    }

}
