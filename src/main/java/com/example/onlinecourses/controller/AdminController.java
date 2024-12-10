package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.Teacher;
import com.example.onlinecourses.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "admin";
    }

    @PostMapping("/admin")
    public String createTeacher(Teacher teacher) {
        if (userService.findByUsername(teacher.getUsername()) != null) {
            return "redirect:/admin?error";
        }
        userService.saveTeacher(teacher);
        return "redirect:/admin";
    }
}
