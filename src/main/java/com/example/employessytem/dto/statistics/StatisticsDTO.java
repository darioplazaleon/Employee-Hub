package com.example.employessytem.dto.statistics;

public record StatisticsDTO(
    Long totalEmployees,
    Long activeEmployees,
    Long inactiveEmployees,
    Long pendingVacationRequests,
    Long approvedVacationRequests,
    Long rejectedVacationRequests) {}
