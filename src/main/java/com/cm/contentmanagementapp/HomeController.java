package com.cm.contentmanagementapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    public String home() {
        return("Welcome");
    }

    @GetMapping("/user")
    public String user() {
            return "Welcome user";
    }

}
