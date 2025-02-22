package com.example.employessytem.controller;

import com.example.employessytem.dto.position.PositionAdd;
import com.example.employessytem.dto.position.PositionDTO;
import com.example.employessytem.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @PostMapping("/create")
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionAdd positionAdd) {
        return ResponseEntity.ok(positionService.createPosition(positionAdd));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PositionDTO> getPositionById(@PathVariable Long id) {
        return ResponseEntity.ok(positionService.getPositionById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PositionDTO> updatePosition(@PathVariable Long id, @RequestBody PositionAdd positionAdd) {
        return ResponseEntity.ok(positionService.updatePosition(id, positionAdd));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok().build();
    }
}
