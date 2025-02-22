package com.example.employessytem.service.impl;

import com.example.employessytem.dto.position.PositionAdd;
import com.example.employessytem.dto.position.PositionDTO;
import com.example.employessytem.entity.Position;
import com.example.employessytem.repository.PositionRepository;
import com.example.employessytem.service.PositionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IPositionService implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public PositionDTO createPosition(PositionAdd positionAdd) {

        var name = positionAdd.name().toUpperCase();

        var position = Position.builder()
                .name(name)
                .build();

        positionRepository.save(position);

        return new PositionDTO(position);
    }

    @Override
    public List<PositionDTO> getAllPositions() {
        return positionRepository.findAll().stream().map(PositionDTO::new).toList();
    }

    @Override
    public PositionDTO getPositionById(Long id) {
        var position = positionRepository.findById(id).orElseThrow();
        return new PositionDTO(position);
    }

    @Override
    public PositionDTO updatePosition(Long id, PositionAdd positionAdd) {
        var position = positionRepository.findById(id).orElseThrow();

        var name = positionAdd.name().toUpperCase();

        position.setName(name);
        positionRepository.save(position);

        return new PositionDTO(position);
    }

    @Override
    public void deletePosition(Long id) {
        positionRepository.deleteById(id);
    }
}
