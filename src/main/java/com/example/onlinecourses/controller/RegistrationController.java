package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.Student;
import com.example.onlinecourses.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(Student student) {

        if (userService.findByUsername(student.getUsername()) != null) {
            return "redirect:/register?error";
        }
        userService.saveStudent(student);
        return "redirect:/login";
    }
}
