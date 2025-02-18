package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.User;
import java.time.LocalDate;

public record EmployeeListDTO(
    Long id, String name, String email, String position, LocalDate createdAt, boolean isActive) {
  public EmployeeListDTO(User user) {
    this(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPosition(),
        user.getCreatedAt(),
        user.isActive());
  }
}
