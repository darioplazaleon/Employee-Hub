package com.example.employessytem.service;

import com.example.employessytem.dto.position.PositionAdd;
import com.example.employessytem.dto.position.PositionDTO;
import java.util.List;

public interface PositionService {
  PositionDTO createPosition(PositionAdd positionAdd);

  List<PositionDTO> getAllPositions();

  PositionDTO getPositionById(Long id);

  PositionDTO updatePosition(Long id, PositionAdd positionAdd);

  void deletePosition(Long id);
}
