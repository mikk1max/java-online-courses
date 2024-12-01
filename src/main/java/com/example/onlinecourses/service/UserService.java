package com.example.onlinecourses.service;

import com.example.onlinecourses.model.User;
import com.example.onlinecourses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            System.out.println("User not found: " + username);
//        } else {
//            System.out.println("User found: " + user.getUsername());
//        }
        return user;
    }


    public void saveUser(User user) {
        // Haszujemy hasło przy użyciu BCryptPasswordEncoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");  // Przydzielamy domyślną rolę
        userRepository.save(user);
    }
}
