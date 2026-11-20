package com.example.demo.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
    @NotBlank String fullName,
    @NotBlank @Email String email,
    @NotBlank String phone,
    @NotBlank String address,
    @NotBlank String city,
    @NotBlank String paymentMethod,
    @NotEmpty List<@Valid Item> items
) {
    public record Item(
        @NotNull Long productId,
        @NotNull @Min(1) @Max(100) Integer quantity
    ) {}
}