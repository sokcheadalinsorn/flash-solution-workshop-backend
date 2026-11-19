package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;

// new improt
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:5173") change to allow all origins
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register API
    // @PostMapping("/register")
    // public ResponseEntity<AuthResponse> register(
    // @Valid @RequestBody RegisterRequest request) {

    // AuthResponse response = authService.register(request);

    // return ResponseEntity.ok(response);
    // }

    // Login API
    // @PostMapping("/login")
    // public ResponseEntity<AuthResponse> login(
    // @Valid @RequestBody LoginRequest request) {

    // AuthResponse response = authService.login(request);

    // return ResponseEntity.ok(response);
    // } update to :

    // Register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletRequest httpRequest) {

        AuthResponse response = authService.register(request);

        createLoginSession(httpRequest, response.getUserId());

        return ResponseEntity.ok(response);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        AuthResponse response = authService.login(request);

        createLoginSession(httpRequest, response.getUserId());

        return ResponseEntity.ok(response);
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.noContent().build();
    }

    // Create a session for the authenticated customer
    private void createLoginSession(
            HttpServletRequest request,
            Long userId) {

        HttpSession session = request.getSession();

        // Renew the session ID after authentication
        request.changeSessionId();

        session.setAttribute("userId", userId);
    }
}