package com.example.employessytem.controller;

import com.example.employessytem.dto.vacation.VacationAdd;
import com.example.employessytem.dto.vacation.VacationDTO;
import com.example.employessytem.service.impl.IVacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vacation")
@RequiredArgsConstructor
public class VacationController {
    private final IVacationService vacationService;

    @PostMapping("/request")
    public ResponseEntity<VacationDTO> requestVacation(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody VacationAdd vacationAdd) {
        return ResponseEntity.ok(vacationService.requestVacation(token, vacationAdd));
    }

    @PutMapping("/approve/{vacationId}")
    public ResponseEntity<VacationDTO> approveVacation(@PathVariable Long vacationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(vacationService.approveVacation(vacationId, token));
    }

    @PutMapping("/reject/{vacationId}")
    public ResponseEntity<VacationDTO> rejectVacation(@PathVariable Long vacationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(vacationService.rejectVacation(vacationId, token));
    }

    @GetMapping("/all")
    public ResponseEntity<List<VacationDTO>> getAllVacations() {
        return ResponseEntity.ok(vacationService.getVacationRequests());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<VacationDTO>> getVacation(@PathVariable Long id) {
        return ResponseEntity.ok(vacationService.getVacationRequests(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacationDTO> getVacationRequest(@PathVariable Long id) {
        return ResponseEntity.ok(vacationService.getVacationRequest(id));
    }


}
