package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = checkUsernameAvailability(user);

        if (signupError == null) {
            signupError = attemptUserCreation(user);
        }

        setModelAttributes(model, signupError);

        return "signup";
    }

    // New method to check username availability
    private String checkUsernameAvailability(User user) {
        if (!userService.isUsernameAvailable(user.getUsername())) {
            return "The username already exists.";
        }
        return null;
    }

    // New method to attempt user creation
    private String attemptUserCreation(User user) {
        int rowsAdded = userService.createUser(user);
        if (rowsAdded < 0) {
            return "There was an error signing you up. Please try again.";
        }
        return null;
    }

    // New method to set model attributes
    private void setModelAttributes(Model model, String signupError) {
        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }
    }
}
