package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.Role;

public record EmployeeAdd(
        String name,
        String email,
        Long positionId,
        Long salary,
        Role role
) {}
