package com.codeanything.modules.test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping("/gettestmsg")
    public Test getTestMsg(@RequestParam(value = "name", defaultValue = "World") String pName) {
        return new Test(0, "Hello " + pName);
	}

    @GetMapping("/getgreetingmsg")
    public String getGreetingMsg(@RequestParam(value = "name", defaultValue = "World") String pName) {
        return "HELLO " + pName;
    }
}