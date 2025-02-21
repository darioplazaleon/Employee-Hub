package com.example.employessytem.repository;

import com.example.employessytem.entity.VacationRequest;
import com.example.employessytem.entity.VacationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<VacationRequest, Long> {
    List<VacationRequest> findAllByEmployeeId(Long employeeId);
    long countByStatus(VacationStatus status);
}
