package com.example.demo.dto;

public record ProfileResponse(
    Long userId,
    String fullName,
    String email,
    String role,
    String phone,
    String skinType
) {}