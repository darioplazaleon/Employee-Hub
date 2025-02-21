package com.example.employessytem.dto.vacation;

import com.example.employessytem.entity.VacationRequest;

import java.time.LocalDate;

public record VacationDTO(
    Long id,
    String title,
    LocalDate createdAt,
    LocalDate startDate,
    LocalDate endDate,
    String comment,
    String status,
    String employeeName,
    String approvedBy
) {
  public VacationDTO(VacationRequest vacationRequest) {
    this(
        vacationRequest.getId(),
        vacationRequest.getTitle(),
        vacationRequest.getRequestDate(),
        vacationRequest.getStartDate(),
        vacationRequest.getEndDate(),
        vacationRequest.getComment(),
        vacationRequest.getStatus().name(),
        vacationRequest.getEmployee().getName(),
        vacationRequest.getApprovedBy() != null ? vacationRequest.getApprovedBy().getName() : null);
  }
}
