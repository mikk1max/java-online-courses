package com.example.onlinecourses.service;

import com.example.onlinecourses.model.User;
import com.example.onlinecourses.repository.UserRepository;
import com.example.onlinecourses.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        // Haszujemy hasło przed zapisaniem w bazie
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
        user.setRole("USER");  // Przydzielamy domyślną rolę
        userRepository.save(user);
    }
}
