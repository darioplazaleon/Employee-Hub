package com.example.employessytem.dto.auth;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {}
