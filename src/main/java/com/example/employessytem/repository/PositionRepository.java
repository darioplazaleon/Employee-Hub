package com.example.employessytem.repository;

import com.example.employessytem.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {}
