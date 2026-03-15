package com.victor.barbearia.barbearia.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FolgaBarbeiroRequestDTO(
        @NotNull LocalDate data
) {
}
