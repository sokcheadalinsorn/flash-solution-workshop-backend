package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
    @NotBlank(message = "Full name is required")
    @Size(max = 255)
    String fullName,

    @Size(max = 30)
    String phone,

    @Size(max = 30)
    String skinType
) {}