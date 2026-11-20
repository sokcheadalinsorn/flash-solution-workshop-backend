
package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Add new user
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setStatus("Invited");

        return userRepository.save(user);
    }

    // Update user
    public User updateUser(Long id, User userDetails) {

        User user = getUserById(id);

        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setStatus(userDetails.getStatus());

        return userRepository.save(user);
    }

    // Update user role
    public User updateUserRole(Long id, String role) {

        User user = getUserById(id);

        user.setRole(role);

        return userRepository.save(user);
    }

    // Update user status
    public User updateUserStatus(Long id, String status) {

        User user = getUserById(id);

        user.setStatus(status);

        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(Long id) {

        User user = getUserById(id);

        userRepository.delete(user);
    }

    // Search users
    public List<User> searchUsers(String keyword) {

        return userRepository.findAll().stream()
                .filter(user ->
                        user.getFullName().toLowerCase().contains(keyword.toLowerCase())
                        || user.getEmail().toLowerCase().contains(keyword.toLowerCase())
                        || user.getRole().toLowerCase().contains(keyword.toLowerCase())
                )
                .toList();
    }
}