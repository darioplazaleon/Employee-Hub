package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.User;

public record EmployeeResponse(
        Long id,
        String name,
        String email,
        String password,
        String position
) {
    public EmployeeResponse(User user, String password) {
    this(user.getId(), user.getName(), user.getEmail(), password, user.getPosition());
    }
}
