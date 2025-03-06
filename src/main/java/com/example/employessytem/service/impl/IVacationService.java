package com.example.employessytem.service.impl;

import com.example.employessytem.dto.vacation.VacationAdd;
import com.example.employessytem.dto.vacation.VacationDTO;
import com.example.employessytem.entity.User;
import com.example.employessytem.entity.VacationRequest;
import com.example.employessytem.entity.VacationStatus;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.repository.VacationRepository;
import com.example.employessytem.service.VacationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IVacationService implements VacationService {

  private final VacationRepository vacationRepository;
  private final UserRepository userRepository;
  private final IEmailService emailService;
  private final IJwtService jwtService;

  @Override
  public VacationDTO requestVacation(String token, VacationAdd vacationAdd) {
    Long employeeId = jwtService.getUserIdFromToken(token);
    User user =
        userRepository
            .findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    var vacation =
        VacationRequest.builder()
            .title(vacationAdd.title())
            .employee(user)
            .startDate(vacationAdd.startDate())
            .endDate(vacationAdd.endDate())
            .comment(vacationAdd.comment())
            .status(VacationStatus.PENDING)
            .build();

    vacationRepository.save(vacation);

    emailService.sendVacationsRequestEmails(user);

    return new VacationDTO(vacation);
  }

  @Override
  public VacationDTO approveVacation(Long vacationId, String token) {
    VacationRequest vacation =
        vacationRepository
            .findById(vacationId)
            .orElseThrow(() -> new RuntimeException("Vacation request not found"));
    Long managerId = jwtService.getUserIdFromToken(token);

    User manager =
        userRepository
            .findById(managerId)
            .orElseThrow(() -> new RuntimeException("Manager not found"));
    vacation.setApprovedBy(manager);
    vacation.setStatus(VacationStatus.APPROVED);
    vacationRepository.save(vacation);

    User user = vacation.getEmployee();
    user.setActive(false);
    userRepository.save(user);

    emailService.sendVacationApprovedEmail(vacation.getEmployee());

    return new VacationDTO(vacation);
  }

  @Override
  public VacationDTO rejectVacation(Long vacationId, String token) {
    VacationRequest vacation =
        vacationRepository
            .findById(vacationId)
            .orElseThrow(() -> new RuntimeException("Vacation request not found"));

    Long managerId = jwtService.getUserIdFromToken(token);
    User manager =
        userRepository
            .findById(managerId)
            .orElseThrow(() -> new RuntimeException("Manager not found"));
    vacation.setApprovedBy(manager);

    vacation.setStatus(VacationStatus.REJECTED);
    vacationRepository.save(vacation);

    emailService.sendVacationRejectedEmail(vacation.getEmployee());
    return new VacationDTO(vacation);
  }

  @Override
  public List<VacationDTO> getVacationRequests(Long employeeId) {
    return vacationRepository.findAllByEmployeeId(employeeId).stream()
        .map(VacationDTO::new)
        .toList();
  }

  @Override
  public List<VacationDTO> getVacationRequests() {
    return vacationRepository.findAll().stream().map(VacationDTO::new).toList();
  }

  @Override
  public VacationDTO getVacationRequest(Long vacationId) {
    return vacationRepository
        .findById(vacationId)
        .map(VacationDTO::new)
        .orElseThrow(() -> new RuntimeException("Vacation request not found"));
  }
}
