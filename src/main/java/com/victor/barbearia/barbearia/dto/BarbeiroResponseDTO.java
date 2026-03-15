package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.Barbeiro;

public record BarbeiroResponseDTO(
        Long id,
        String nome,
        String telefone,
        String email
) {

    public static BarbeiroResponseDTO from(Barbeiro barbeiro) {
        return new BarbeiroResponseDTO(
                barbeiro.getId(),
                barbeiro.getNome(),
                barbeiro.getTelefone(),
                barbeiro.getEmail()
        );
    }
}
