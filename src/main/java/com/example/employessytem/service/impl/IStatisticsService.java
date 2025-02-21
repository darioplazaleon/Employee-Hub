package com.example.employessytem.service.impl;

import com.example.employessytem.dto.statistics.StatisticsDTO;
import com.example.employessytem.entity.VacationStatus;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.repository.VacationRepository;
import com.example.employessytem.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IStatisticsService implements StatisticsService {

  private final UserRepository userRepository;
  private final VacationRepository vacationRepository;

  @Override
  public StatisticsDTO getGeneralStatistics() {
    long totalEmployees = userRepository.count();
    long activeEmployees = userRepository.countByActive(true);
    long inactiveEmployees = userRepository.countByActive(false);

    long pendingVacationRequests = vacationRepository.countByStatus(VacationStatus.PENDING);
    long approvedVacationRequests = vacationRepository.countByStatus(VacationStatus.APPROVED);
    long rejectedVacationRequests = vacationRepository.countByStatus(VacationStatus.REJECTED);

    return new StatisticsDTO(
        totalEmployees,
        activeEmployees,
        inactiveEmployees,
        pendingVacationRequests,
        approvedVacationRequests,
        rejectedVacationRequests);
  }
}
