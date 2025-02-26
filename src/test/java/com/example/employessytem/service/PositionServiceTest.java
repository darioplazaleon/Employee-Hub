package com.example.employessytem.service;

import com.example.employessytem.dto.position.PositionAdd;
import com.example.employessytem.dto.position.PositionDTO;
import com.example.employessytem.entity.Position;
import com.example.employessytem.repository.PositionRepository;
import com.example.employessytem.service.impl.IPositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PositionServiceTest {

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private IPositionService positionService;

    private PositionAdd positionAdd;
    private Position position;

    @BeforeEach
    void setUp() {
        positionAdd = new PositionAdd("Developer");

        position = Position.builder()
                .id(1L)
                .name("DEVELOPER")
                .build();
    }

    @Test
    void createPosition_Success() {
        when(positionRepository.save(any(Position.class))).thenReturn(position);

        PositionDTO result = positionService.createPosition(positionAdd);

        assertNotNull(result);
        assertEquals("DEVELOPER", result.name());
        verify(positionRepository, times(1)).save(any(Position.class));
    }

    @Test
    void getAllPositions_Success() {
        when(positionRepository.findAll()).thenReturn(List.of(position));

        List<PositionDTO> result = positionService.getAllPositions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("DEVELOPER", result.get(0).name());
        verify(positionRepository, times(1)).findAll();
    }

    // ✅ TEST PARA getPositionById()
    @Test
    void getPositionById_Success() {
        when(positionRepository.findById(1L)).thenReturn(Optional.of(position));

        PositionDTO result = positionService.getPositionById(1L);

        assertNotNull(result);
        assertEquals("DEVELOPER", result.name());
        verify(positionRepository, times(1)).findById(1L);
    }

    @Test
    void getPositionById_NotFound() {
        when(positionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            positionService.getPositionById(1L);
        });
    }

    // ✅ TEST PARA updatePosition()
    @Test
    void updatePosition_Success() {
        when(positionRepository.findById(1L)).thenReturn(Optional.of(position));
        when(positionRepository.save(any(Position.class))).thenReturn(position);

        PositionDTO result = positionService.updatePosition(1L, positionAdd);

        assertNotNull(result);
        assertEquals("DEVELOPER", result.name());
        verify(positionRepository, times(1)).save(any(Position.class));
    }

    @Test
    void updatePosition_NotFound() {
        when(positionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            positionService.updatePosition(1L, positionAdd);
        });
    }

    // ✅ TEST PARA deletePosition()
    @Test
    void deletePosition_Success() {
        doNothing().when(positionRepository).deleteById(1L);

        assertDoesNotThrow(() -> positionService.deletePosition(1L));
        verify(positionRepository, times(1)).deleteById(1L);
    }


}
