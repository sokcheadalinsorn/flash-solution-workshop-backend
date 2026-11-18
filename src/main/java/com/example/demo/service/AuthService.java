package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Register
    public AuthResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Create new user
        User user = new User(
                request.getFullName(),
                request.getEmail(),
                hashedPassword
        );

        // Save user to database
        User savedUser = userRepository.save(user);

     return new AuthResponse(
        "Registration successful",
        savedUser.getId(),
        savedUser.getFullName(),
        savedUser.getEmail(),
        savedUser.getRole()
);
    }

    // Login
    public AuthResponse login(LoginRequest request) {

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

 return new AuthResponse(
        "Login successful",
        user.getId(),
        user.getFullName(),
        user.getEmail(),
        user.getRole()
);
    }
}