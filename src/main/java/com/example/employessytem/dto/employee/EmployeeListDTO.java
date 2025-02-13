package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.User;

import java.util.Date;

public record EmployeeListDTO(
        Long id,
        String name,
        String email,
        String position
) {
    public EmployeeListDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPosition());
    }
}
