package com.example.employessytem.dto.employee;

import com.example.employessytem.dto.vacation.VacationDTO;
import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record EmployeeDTO(
    Long id,
    String name,
    String email,
    String position,
    Role role,
    Long salary,
    LocalDate createdAt,
    List<VacationDTO> vacationRequests) {
  public EmployeeDTO(User user) {
    this(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPosition().getName(),
        user.getRole(),
        user.getSalary(),
        user.getCreatedAt(),
        Optional.ofNullable(user.getVacationRequests()).orElse(List.of()).stream()
            .map(VacationDTO::new)
            .toList());
  }
}
