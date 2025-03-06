package com.example.employessytem.controller;

import com.example.employessytem.dto.statistics.StatisticsDTO;
import com.example.employessytem.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final StatisticsService statisticsService;

  @GetMapping("/general")
  public ResponseEntity<StatisticsDTO> getGeneralStatistics() {
    return ResponseEntity.ok(statisticsService.getGeneralStatistics());
  }
}
