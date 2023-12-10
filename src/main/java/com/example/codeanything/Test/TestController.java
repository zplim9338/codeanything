package com.example.codeanything.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TestController {
    @GetMapping("/test/gettestmsg")
    public Test getTestMsg(@RequestParam(value = "name", defaultValue = "World") String pName) {
        return new Test(0, "Hello " + pName);
	}

    @GetMapping("/test/getgreetingmsg")
    public String getGreetingMsg(@RequestParam(value = "name", defaultValue = "World") String pName) {
        return "HELLO " + pName;
    }
}