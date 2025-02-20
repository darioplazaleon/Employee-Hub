package com.example.employessytem.controller;

import com.example.employessytem.dto.TokenResponse;
import com.example.employessytem.dto.auth.ChangePasswordRequest;
import com.example.employessytem.dto.auth.LoginRequest;
import com.example.employessytem.service.impl.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final IAuthService authService;

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> loginEmployee(@RequestBody LoginRequest employeeLogin) {
    return ResponseEntity.ok(authService.login(employeeLogin));
  }

  @PostMapping("/refresh")
  public ResponseEntity<TokenResponse> refreshToken(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    return ResponseEntity.ok(authService.refreshToken(authorizationHeader));
  }

  @PostMapping("/change-password")
  public ResponseEntity<Void> changePassword(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
      @RequestBody ChangePasswordRequest changePasswordRequest) {
    authService.changePassword(changePasswordRequest, authorizationHeader);
    return ResponseEntity.ok().build();
  }
}
