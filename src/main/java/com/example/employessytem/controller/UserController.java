package com.example.employessytem.controller;

import com.example.employessytem.dto.TokenResponse;
import com.example.employessytem.dto.employee.EmployeeAdd;
import com.example.employessytem.dto.employee.EmployeeLogin;
import com.example.employessytem.dto.employee.EmployeeResponse;
import com.example.employessytem.service.impl.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IAuthService authService;

    @PostMapping("/register")
    private ResponseEntity<EmployeeResponse> registerEmployee(@RequestBody EmployeeAdd employeeAdd) {
        return ResponseEntity.ok(authService.registerEmployee(employeeAdd));
    }

    @PostMapping("/login")
    private ResponseEntity<TokenResponse> loginEmployee(@RequestBody EmployeeLogin employeeLogin) {
        return ResponseEntity.ok(authService.login(employeeLogin));
    }
}
