package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.Servico;

import java.math.BigDecimal;

public record ServicoResponseDTO(
        Long id,
        String nomeServico,
        String descricaoServico,
        BigDecimal valorServico,
        Integer duracaoMinutos
) {

    public static ServicoResponseDTO from(Servico servico) {
        return new ServicoResponseDTO(
                servico.getId(),
                servico.getNomeServico(),
                servico.getDescricaoServico(),
                servico.getValorServico(),
                servico.getDuracaoMinutos()
        );
    }
}
