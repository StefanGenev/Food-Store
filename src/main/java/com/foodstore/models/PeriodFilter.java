package com.foodstore.models;

// Транспортен клас с данни за филтър по период

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PeriodFilter {
    // Дата от
    private LocalDate dateFrom;

    // Дата до
    private LocalDate dateTo;
}
