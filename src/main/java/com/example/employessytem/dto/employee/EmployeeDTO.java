package com.example.employessytem.dto.employee;

import com.example.employessytem.dto.vacation.VacationDTO;
import com.example.employessytem.entity.User;
import com.example.employessytem.entity.VacationRequest;

import java.time.LocalDate;
import java.util.List;

public record EmployeeDTO(
        Long id,
        String name,
        String email,
        String position,
        Long salary,
        LocalDate createdAt,
        List<VacationDTO> vacationRequests
) {
    public EmployeeDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPosition(), user.getSalary(), user.getCreatedAt(), user.getVacationRequests().stream().map(VacationDTO::new).toList());
    }
}
