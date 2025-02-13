package com.example.employessytem.dto.vacation;

import java.time.LocalDate;

public record VacationAdd(
        LocalDate startDate,
        LocalDate endDate,
        String comment
) {}
