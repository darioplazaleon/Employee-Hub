package com.example.employessytem.service;

import com.example.employessytem.dto.TokenResponse;
import com.example.employessytem.dto.auth.ChangePasswordRequest;
import com.example.employessytem.dto.auth.LoginRequest;

public interface AuthService {
    TokenResponse login(LoginRequest employeeLogin);
    TokenResponse refreshToken(String authentication);
    void changePassword(ChangePasswordRequest changePasswordRequest, String token);
}
