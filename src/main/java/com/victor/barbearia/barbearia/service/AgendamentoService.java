package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Agendamento;
import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.domain.Cliente;
import com.victor.barbearia.barbearia.repository.AgendamentoRepository;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import com.victor.barbearia.barbearia.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ClienteRepository clienteRepository;

    public Agendamento criar(Agendamento agendamento) {

        LocalDateTime dataHoraNormalizada = normalizar(
                agendamento.getDataHora());
                agendamento.setDataHora(dataHoraNormalizada);

        long clienteId = agendamento.getCliente().getId();
        long barbeiroId = agendamento.getBarbeiro().getId();

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalStateException("Cliente não encontrado"));

        Barbeiro barbeiro = barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new IllegalStateException("Barbeiro não encontrado"));


        boolean existeAgendamento = agendamentoRepository.existsByBarbeiro_IdAndDataHora(
                barbeiroId,
                dataHoraNormalizada
        );

        if (existeAgendamento) {
            throw new IllegalStateException("Horario indisponivel para este barbeiro");
        }

        agendamento.setBarbeiro(barbeiro);
        agendamento.setCliente(cliente);

        return agendamentoRepository.save(agendamento);
    }

    private LocalDateTime normalizar(LocalDateTime dataHora) {
        if (dataHora == null) {
            return null;
        }
        return dataHora.withSecond(0).withNano(0);
    }

}
