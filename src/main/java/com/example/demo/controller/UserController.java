
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers(
            @RequestParam(required = false) String search) {

        if (search != null && !search.isBlank()) {
            return userService.searchUsers(search);
        }

        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Create new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Update user
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        return userService.updateUser(id, user);
    }

    // Update user role
    @PatchMapping("/{id}/role")
    public User updateUserRole(
            @PathVariable Long id,
            @RequestBody User user) {

        return userService.updateUserRole(id, user.getRole());
    }

    // Update user status
    @PatchMapping("/{id}/status")
    public User updateUserStatus(
            @PathVariable Long id,
            @RequestBody User user) {

        return userService.updateUserStatus(id, user.getStatus());
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}