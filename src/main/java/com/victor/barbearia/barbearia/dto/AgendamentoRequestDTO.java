package com.victor.barbearia.barbearia.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoRequestDTO(

        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Barbeiro é obrigatório")
        Long barbeiroId,

        @NotNull(message = "Data é obrigatória")
        LocalDate data,

        @NotNull(message = "Hora é obrigatória")
        LocalTime hora,

        @NotEmpty(message = "Selecione ao menos um serviço")
        List<Long> servicosId
) {}
