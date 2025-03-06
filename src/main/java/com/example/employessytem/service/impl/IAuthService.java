package com.example.employessytem.service.impl;

import com.example.employessytem.dto.TokenResponse;
import com.example.employessytem.dto.auth.ChangePasswordRequest;
import com.example.employessytem.dto.auth.LoginRequest;
import com.example.employessytem.entity.User;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IAuthService implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final IEmailService emailService;
  private final AuthenticationManager authenticationManager;
  private final IJwtService jwtService;

  @Override
  public TokenResponse login(LoginRequest employeeLogin) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(employeeLogin.email(), employeeLogin.password()));

    User user =
        userRepository
            .findByEmail(employeeLogin.email())
            .orElseThrow(() -> new RuntimeException("User not found"));

    String accessToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    return new TokenResponse(accessToken, refreshToken);
  }

  @Override
  public TokenResponse refreshToken(String authentication) {
    if (authentication == null || !authentication.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Invalid auth header");
    }
    final String refreshToken = authentication.substring(7);
    final String userEmail = jwtService.getSubject(refreshToken);
    if (userEmail == null) {
      return null;
    }

    final User user = userRepository.findByEmail(userEmail).orElseThrow();
    final boolean isTokenValid = jwtService.isValidTokenPerUser(refreshToken, userEmail);
    if (!isTokenValid) {
      return null;
    }

    final String accessToken = jwtService.generateRefreshToken(user);

    return new TokenResponse(accessToken, refreshToken);
  }

  @Override
  public void changePassword(ChangePasswordRequest changePasswordRequest, String token) {
    Long userId = jwtService.getUserIdFromToken(token);

    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Invalid password");
    }

    if (passwordEncoder.matches(changePasswordRequest.newPassword(), user.getPassword())) {
      throw new IllegalArgumentException(
          "New password must be different from the current password");
    }

    if (!isPasswordValid(changePasswordRequest.newPassword())) {
      throw new IllegalArgumentException(
          "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character");
    }

    user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
    userRepository.save(user);
  }

  private boolean isPasswordValid(String password) {
    if (password.length() < 8) {
      return false;
    }

    boolean hasUppercase = false;
    boolean hasLowercase = false;
    boolean hasDigit = false;
    boolean hasSpecialChar = false;

    for (char c : password.toCharArray()) {
      if (Character.isUpperCase(c)) {
        hasUppercase = true;
      } else if (Character.isLowerCase(c)) {
        hasLowercase = true;
      } else if (Character.isDigit(c)) {
        hasDigit = true;
      } else if (!Character.isLetterOrDigit(c)) {
        hasSpecialChar = true;
      }
    }
    return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
  }
}
