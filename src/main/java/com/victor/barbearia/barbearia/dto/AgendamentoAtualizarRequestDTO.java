package com.victor.barbearia.barbearia.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoAtualizarRequestDTO(
        @NotNull Long barbeiroId,
        @NotNull LocalDate data,
        @NotNull LocalTime hora,
        @NotEmpty List<Long> servicosId
) {
}
