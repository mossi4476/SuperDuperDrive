package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    public LoginController() {
        // Constructor for potential future dependencies
    }

    @GetMapping
    public String loginView() {
        return "login";
    }
}
