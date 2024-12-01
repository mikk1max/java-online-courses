package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.User;
import com.example.onlinecourses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return "redirect:/register?error"; // Jeśli użytkownik istnieje
        }
        userService.saveUser(user);
        return "redirect:/login?success"; // Po udanej rejestracji przekieruj do logowania
    }
}
