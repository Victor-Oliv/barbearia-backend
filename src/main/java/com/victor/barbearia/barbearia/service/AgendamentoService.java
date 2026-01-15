package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.*;
import com.victor.barbearia.barbearia.repository.AgendamentoRepository;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import com.victor.barbearia.barbearia.repository.ClienteRepository;
import com.victor.barbearia.barbearia.repository.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;

    public Agendamento criar(Agendamento agendamento) {

        if (agendamento.getCliente() == null || agendamento.getCliente().getId() == null) {
            throw new IllegalArgumentException("cliente é obrigatório");
        }
        if (agendamento.getBarbeiro() == null || agendamento.getBarbeiro().getId() == null) {
            throw new IllegalArgumentException("barbeiro é obrigatório");
        }
        if (agendamento.getDataHora() == null) {
            throw new IllegalArgumentException("data e Hora é obrigatória");
        }
        if (agendamento.getServicosId() == null || agendamento.getServicosId().isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos um serviço");
        }

        LocalDateTime dataHoraNormalizada = normalizarObrigatoria(agendamento.getDataHora());
        agendamento.setDataHora(dataHoraNormalizada);

        Long clienteId = agendamento.getCliente().getId();
        Long barbeiroId = agendamento.getBarbeiro().getId();

        boolean existeAgendamento = agendamentoRepository.existsByBarbeiro_IdAndDataHora(
                barbeiroId,
                dataHoraNormalizada
        );
        if (existeAgendamento) {
            throw new IllegalStateException("Horario indisponivel para este barbeiro");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalStateException("Cliente não encontrado"));

        Barbeiro barbeiro = barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new IllegalStateException("Barbeiro não encontrado"));

        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (Long servicoId : agendamento.getServicosId()) {
            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new IllegalStateException("Servico não encontrado: " + servicoId));

            AgendamentoItem item = new AgendamentoItem();
            item.setAgendamento(agendamento);
            item.setServico(servico);
            item.setValor(servico.getValorServico());

            agendamento.getItens().add(item);
            valorTotal = valorTotal.add(servico.getValorServico());
        }

        agendamento.setValorTotal(valorTotal);

        return agendamentoRepository.save(agendamento);
    }

    private LocalDateTime normalizarObrigatoria(LocalDateTime dataHora) {
        return dataHora.withSecond(0).withNano(0);
    }

}
