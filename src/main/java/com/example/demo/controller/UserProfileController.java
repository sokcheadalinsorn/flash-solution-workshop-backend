package com.example.demo.controller;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.dto.UpdateProfileRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class UserProfileController {

    private final UserRepository userRepository;

    public UserProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /api/users/me
    @GetMapping("/me")
    public ProfileResponse getMyProfile(
            HttpServletRequest request) {

        User user = getCurrentUser(request);

        return toResponse(user);
    }

    // PUT /api/users/me
    @PutMapping("/me")
    public ProfileResponse updateMyProfile(
            @Valid @RequestBody UpdateProfileRequest data,
            HttpServletRequest request) {

        User user = getCurrentUser(request);

        user.setFullName(data.fullName().trim());

        user.setPhone(
            data.phone() == null ? "" : data.phone().trim()
        );

        user.setSkinType(
            data.skinType() == null ? "" : data.skinType()
        );

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    // Identify the user from the server session
    private User getCurrentUser(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null ||
            !(session.getAttribute("userId") instanceof Long userId)) {

            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Please log in first"
            );
        }

        return userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Customer not found"
            ));
    }

    private ProfileResponse toResponse(User user) {

        return new ProfileResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole(),
            user.getPhone(),
            user.getSkinType()
        );
    }
}