package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String telefone,
        String email
) {

    public static ClienteResponseDTO from(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail()
        );
    }
}
