package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.Agendamento;
import com.victor.barbearia.barbearia.domain.StatusAgendamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoResponseDTO(
        Long id,
        ClienteResponseDTO cliente,
        BarbeiroResponseDTO barbeiro,
        LocalDate data,
        LocalTime hora,
        BigDecimal valorTotal,
        StatusAgendamento status,
        List<AgendamentoItemResponseDTO> itens
) {

    public static AgendamentoResponseDTO from(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                ClienteResponseDTO.from(agendamento.getCliente()),
                BarbeiroResponseDTO.from(agendamento.getBarbeiro()),
                agendamento.getData(),
                agendamento.getHora(),
                agendamento.getValorTotal(),
                agendamento.getStatus() != null ? agendamento.getStatus() : StatusAgendamento.AGENDADO,
                agendamento.getItens().stream()
                        .map(AgendamentoItemResponseDTO::from)
                        .toList()
        );
    }
}
