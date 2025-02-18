package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.Role;

public record EmployeeAdd(
        String name,
        String email,
        String position,
        Long salary,
        Role role
) {}
