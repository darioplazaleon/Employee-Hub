package com.example.employessytem.service;

import com.example.employessytem.dto.vacation.VacationAdd;
import com.example.employessytem.dto.vacation.VacationDTO;
import com.example.employessytem.entity.VacationRequest;

import java.time.LocalDate;
import java.util.List;

public interface VacationService {
    VacationDTO requestVacation(String token, VacationAdd vacationAdd);
    VacationDTO approveVacation(Long vacationId);
    VacationDTO rejectVacation(Long vacationId);
    List<VacationDTO> getVacationRequests(Long employeeId);
    List<VacationDTO> getVacationRequests();
    VacationDTO getVacationRequest(Long vacationId);
}
