package com.example.employessytem.service;

import com.example.employessytem.dto.vacation.VacationAdd;
import com.example.employessytem.dto.vacation.VacationDTO;
import java.util.List;

public interface VacationService {
  VacationDTO requestVacation(String token, VacationAdd vacationAdd);

  VacationDTO approveVacation(Long vacationId, String token);

  VacationDTO rejectVacation(Long vacationId, String token);

  List<VacationDTO> getVacationRequests(Long employeeId);

  List<VacationDTO> getVacationRequests();

  VacationDTO getVacationRequest(Long vacationId);
}
