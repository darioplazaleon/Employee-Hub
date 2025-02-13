package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.User;

import java.util.Date;

public record EmployeeDTO(
        Long id,
        String name,
        String email,
        String position,
        Long salary,
        Date entry_date
) {
    public EmployeeDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPosition(), user.getSalary(), user.getCreatedAt());
    }
}
