package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.FolgaBarbeiro;

import java.time.LocalDate;

public record FolgaBarbeiroResponseDTO(
        Long id,
        Long barbeiroId,
        LocalDate data
) {
    public static FolgaBarbeiroResponseDTO from(FolgaBarbeiro f) {
        return new FolgaBarbeiroResponseDTO(f.getId(), f.getBarbeiro().getId(), f.getData());
    }
}
