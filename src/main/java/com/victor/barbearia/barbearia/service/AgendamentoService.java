package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Agendamento;
import com.victor.barbearia.barbearia.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento criar(Agendamento agendamento) {

        LocalDateTime dataHoraNormalizada = normalizar(
                agendamento.getDataHora());
                agendamento.setDataHora(dataHoraNormalizada);

        boolean existeAgendamento = agendamentoRepository.existsByBarbeiroIdAndDataHora(
                agendamento.getBarbeiroId(),
                agendamento.getDataHora()
        );

        if (existeAgendamento) {
            throw new IllegalStateException("Horario indisponivel para este barbeiro");
        }

        return agendamentoRepository.save(agendamento);
    }

    private LocalDateTime normalizar(LocalDateTime dataHora) {
        if (dataHora == null) {
            return null;
        }
        return dataHora.withSecond(0).withNano(0);
    }

}
