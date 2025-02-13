package com.example.employessytem.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {}
