package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.User;

import java.util.Date;

public record EmployeeResponse(
        Long id,
        String name,
        String email,
        String password,
        String position,
        Date entry_date
) {
    public EmployeeResponse(User user, String password) {
    this(user.getId(), user.getName(), user.getEmail(), password, user.getPosition(), user.getCreatedAt());
    }
}
