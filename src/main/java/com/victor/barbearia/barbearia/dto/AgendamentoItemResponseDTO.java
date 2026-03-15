package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.AgendamentoItem;

import java.math.BigDecimal;

public record AgendamentoItemResponseDTO(
        Long id,
        ServicoResponseDTO servico,
        BigDecimal valor
) {

    public static AgendamentoItemResponseDTO from(AgendamentoItem item) {
        return new AgendamentoItemResponseDTO(
                item.getId(),
                ServicoResponseDTO.from(item.getServico()),
                item.getValor()
        );
    }
}
