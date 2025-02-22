package com.example.employessytem.dto.position;

import com.example.employessytem.entity.Position;

public record PositionDTO(
    Long id,
    String name
) {
    public PositionDTO(Position position) {
        this(position.getId(), position.getName());
    }
}
