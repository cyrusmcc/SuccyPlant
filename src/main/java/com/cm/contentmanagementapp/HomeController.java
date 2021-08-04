package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.user.User;
import com.cm.contentmanagementapp.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/login")
    public String showLoginForm() {
        return "login-form";
    }

    @ResponseBody
    @GetMapping("/hello")
    public String showHello() {
        return "Hello!";
    }

    @ResponseBody
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register-form";
    }

    @ResponseBody
    @PostMapping("/process-register")
    public String processRegistration(User user) {

        userService.save(user);
        return "register-success";
    }

    @ResponseBody
    @GetMapping("/user")
    public String user() {
        return "Welcome user";
    }

}
