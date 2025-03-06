package com.example.employessytem.dto.employee;

import com.example.employessytem.entity.Position;
import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import java.time.LocalDate;

public record EmployeeListDTO(
    Long id,
    String name,
    String email,
    Role role,
    Position position,
    LocalDate createdAt,
    boolean isActive) {
  public EmployeeListDTO(User user) {
    this(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getRole(),
        user.getPosition(),
        user.getCreatedAt(),
        user.isActive());
  }
}
