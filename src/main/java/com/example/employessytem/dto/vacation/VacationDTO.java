package com.example.employessytem.dto.vacation;

import com.example.employessytem.entity.VacationRequest;

public record VacationDTO(
    Long id,
    String startDate,
    String endDate,
    String comment,
    String status,
    String employeeName,
    String approvedBy) {
  public VacationDTO(VacationRequest vacationRequest) {
    this(
        vacationRequest.getId(),
        vacationRequest.getStartDate().toString(),
        vacationRequest.getEndDate().toString(),
        vacationRequest.getComment(),
        vacationRequest.getStatus().name(),
        vacationRequest.getEmployee().getName(),
        vacationRequest.getApprovedBy() != null ? vacationRequest.getApprovedBy().getName() : null);
  }
}
