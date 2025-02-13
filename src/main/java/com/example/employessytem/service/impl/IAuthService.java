package com.example.employessytem.service.impl;

import com.example.employessytem.dto.TokenResponse;
import com.example.employessytem.dto.employee.EmployeeAdd;
import com.example.employessytem.dto.employee.EmployeeLogin;
import com.example.employessytem.dto.employee.EmployeeResponse;
import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.service.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class IAuthService implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IEmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;

    @Override
    public EmployeeResponse registerEmployee(EmployeeAdd employeeAdd) {
        Role role = determineRole(employeeAdd);

        String randomPassword = generateRandomPassword();

        var user = User.builder()
                .email(employeeAdd.email())
                .position(employeeAdd.position())
                .name(employeeAdd.name())
                .role(role)
                .password(passwordEncoder.encode(randomPassword))
                .build();

        var savedUser = userRepository.save(user);

        try {
            emailService.sendCredentialsEmail(employeeAdd.email(), employeeAdd.email(), randomPassword);
        } catch (MessagingException e) {
            // Handle the exception, e.g., log the error
            e.printStackTrace();
        }

        return new EmployeeResponse(savedUser, randomPassword);
    }

    @Override
    public TokenResponse login(EmployeeLogin employeeLogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeLogin.email(), employeeLogin.password()));

        User user = userRepository.findByEmail(employeeLogin.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(user);
        return new TokenResponse(accessToken, "refreshToken");
    }

    private Role determineRole(EmployeeAdd employeeAdd) {
        if ("manager".equalsIgnoreCase(employeeAdd.position())) {
            return Role.MANAGER;
        } else {
            return Role.USER;
        }
    }

    private static String generateRandomPassword() {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
