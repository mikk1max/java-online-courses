package com.example.onlinecourses.service;

import com.example.onlinecourses.model.User;
import com.example.onlinecourses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        // Hash the password using BCryptPasswordEncoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");  // Assign a default role
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
