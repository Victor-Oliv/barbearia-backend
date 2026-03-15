package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.*;
import com.victor.barbearia.barbearia.dto.AgendamentoAtualizarRequestDTO;
import com.victor.barbearia.barbearia.dto.AgendamentoRequestDTO;
import com.victor.barbearia.barbearia.dto.AgendamentoResponseDTO;
import com.victor.barbearia.barbearia.dto.HorarioFuncionamentoDTO;
import com.victor.barbearia.barbearia.exception.CancelamentoNaoPermitidoException;
import com.victor.barbearia.barbearia.exception.HorarioIndisponivelException;
import com.victor.barbearia.barbearia.exception.RecursoNaoEncontradoException;
import com.victor.barbearia.barbearia.repository.AgendamentoRepository;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import com.victor.barbearia.barbearia.repository.ClienteRepository;
import com.victor.barbearia.barbearia.repository.FolgaBarbeiroRepository;
import com.victor.barbearia.barbearia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;
    private final FolgaBarbeiroRepository folgaRepository;
    private final HorarioFuncionamentoService horarioService;

    @Transactional
    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + dto.clienteId()));

        Barbeiro barbeiro = barbeiroRepository.findById(dto.barbeiroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Barbeiro não encontrado: " + dto.barbeiroId()));

        if (folgaRepository.existsByBarbeiro_IdAndData(dto.barbeiroId(), dto.data())) {
            throw new HorarioIndisponivelException("Barbeiro está de folga neste dia.");
        }

        HorarioFuncionamentoDTO horario = horarioService.buscar();
        validarDiaAberto(dto.data(), horario);
        validarDentroDoHorario(dto.data(), dto.hora(), horario);

        List<Servico> servicos = carregarServicos(dto.servicosId());
        BigDecimal valorTotal = calcularValorTotal(servicos);

        int duracaoNovo = calcularDuracao(servicos);
        LocalTime fimNovo = dto.hora().plusMinutes(duracaoNovo);

        LocalTime fechamentoDia = getFechamentoEfetivo(horario, dto.data());
        if (!fimNovo.isBefore(fechamentoDia) && !fimNovo.equals(fechamentoDia)) {
            throw new HorarioIndisponivelException(
                    "O serviço ultrapassa o horário de fechamento (" + fechamentoDia.toString().substring(0, 5) + ").");
        }

        verificarConflitos(dto.barbeiroId(), dto.data(), dto.hora(), fimNovo, null);

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setData(dto.data());
        agendamento.setHora(dto.hora());

        for (Servico servico : servicos) {
            AgendamentoItem item = new AgendamentoItem();
            item.setAgendamento(agendamento);
            item.setServico(servico);
            item.setValor(servico.getValorServico());
            agendamento.getItens().add(item);
        }

        agendamento.setValorTotal(valorTotal);

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return AgendamentoResponseDTO.from(salvo);
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponseDTO> listar() {
        return agendamentoRepository.findAll()
                .stream()
                .map(AgendamentoResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado: " + id));
        return AgendamentoResponseDTO.from(agendamento);
    }

    @Transactional
    public void cancelar(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado: " + id));

        LocalDateTime agendamentoAt = LocalDateTime.of(agendamento.getData(), agendamento.getHora());
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), agendamentoAt);

        if (horasRestantes < 2) {
            throw new CancelamentoNaoPermitidoException(
                    "Cancelamento não permitido: só é possível cancelar com no mínimo 2 horas de antecedência.");
        }

        agendamentoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponseDTO> listarPorCliente(Long clienteId) {
        return agendamentoRepository.findByCliente_Id(clienteId)
                .stream()
                .map(AgendamentoResponseDTO::from)
                .toList();
    }

    @Transactional
    public AgendamentoResponseDTO atualizar(Long id, AgendamentoAtualizarRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado: " + id));

        Barbeiro barbeiro = barbeiroRepository.findById(dto.barbeiroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Barbeiro não encontrado: " + dto.barbeiroId()));

        if (folgaRepository.existsByBarbeiro_IdAndData(dto.barbeiroId(), dto.data())) {
            throw new HorarioIndisponivelException("Barbeiro está de folga neste dia.");
        }

        HorarioFuncionamentoDTO horario = horarioService.buscar();
        validarDiaAberto(dto.data(), horario);
        validarDentroDoHorario(dto.data(), dto.hora(), horario);

        List<Servico> servicos = carregarServicos(dto.servicosId());
        BigDecimal valorTotal = calcularValorTotal(servicos);

        int duracaoNovo = calcularDuracao(servicos);
        LocalTime fimNovo = dto.hora().plusMinutes(duracaoNovo);

        LocalTime fechamentoDiaAtualizar = getFechamentoEfetivo(horario, dto.data());
        if (!fimNovo.isBefore(fechamentoDiaAtualizar) && !fimNovo.equals(fechamentoDiaAtualizar)) {
            throw new HorarioIndisponivelException(
                    "O serviço ultrapassa o horário de fechamento (" + fechamentoDiaAtualizar.toString().substring(0, 5) + ").");
        }

        verificarConflitos(dto.barbeiroId(), dto.data(), dto.hora(), fimNovo, id);

        agendamento.setBarbeiro(barbeiro);
        agendamento.setData(dto.data());
        agendamento.setHora(dto.hora());

        // Reconstrói os itens (orphanRemoval = true cuida da limpeza)
        agendamento.getItens().clear();
        for (Servico servico : servicos) {
            AgendamentoItem item = new AgendamentoItem();
            item.setAgendamento(agendamento);
            item.setServico(servico);
            item.setValor(servico.getValorServico());
            agendamento.getItens().add(item);
        }

        agendamento.setValorTotal(valorTotal);

        return AgendamentoResponseDTO.from(agendamentoRepository.save(agendamento));
    }

    @Transactional
    public AgendamentoResponseDTO atualizarStatus(Long id, StatusAgendamento novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado: " + id));
        agendamento.setStatus(novoStatus);
        return AgendamentoResponseDTO.from(agendamentoRepository.save(agendamento));
    }

    @Transactional(readOnly = true)
    public List<String> horariosDisponiveis(Long barbeiroId, LocalDate data, List<Long> servicosId) {
        if (!barbeiroRepository.existsById(barbeiroId)) {
            throw new RecursoNaoEncontradoException("Barbeiro não encontrado: " + barbeiroId);
        }

        if (folgaRepository.existsByBarbeiro_IdAndData(barbeiroId, data)) {
            return List.of();
        }

        HorarioFuncionamentoDTO horario = horarioService.buscar();

        if (horario.diasAbertos() != null && !horario.diasAbertos().isEmpty()
                && !horario.diasAbertos().contains(data.getDayOfWeek().name())) {
            return List.of();
        }

        int duracaoServicos = 30;
        if (servicosId != null && !servicosId.isEmpty()) {
            duracaoServicos = Math.max(1, servicoRepository.findAllById(servicosId)
                    .stream()
                    .mapToInt(s -> s.getDuracaoMinutos() != null ? s.getDuracaoMinutos() : 0)
                    .sum());
            if (duracaoServicos == 0) duracaoServicos = 30;
        }

        List<Agendamento> agendamentos = agendamentoRepository.findByBarbeiro_IdAndData(barbeiroId, data);

        LocalTime aberturaDia = getAberturaEfetiva(horario, data);
        LocalTime fechamentoDia = getFechamentoEfetivo(horario, data);
        LocalTime limiteFinal = fechamentoDia.minusMinutes(duracaoServicos);
        LocalTime agora = LocalTime.now();
        boolean isHoje = data.isEqual(LocalDate.now());

        // Candidatos: slots de 30 em 30 minutos + horários de fim dos agendamentos existentes
        // Isso garante que ao terminar um serviço o próximo slot seja oferecido imediatamente
        java.util.TreeSet<LocalTime> candidatos = new java.util.TreeSet<>();
        LocalTime c = aberturaDia;
        while (!c.isAfter(limiteFinal)) {
            candidatos.add(c);
            c = c.plusMinutes(30);
        }
        for (Agendamento ag : agendamentos) {
            int durExist = Math.max(1, ag.getItens().stream()
                    .mapToInt(i -> i.getServico().getDuracaoMinutos() != null ? i.getServico().getDuracaoMinutos() : 0)
                    .sum());
            LocalTime endTime = ag.getHora().plusMinutes(durExist);
            if (!endTime.isBefore(aberturaDia) && !endTime.isAfter(limiteFinal)) {
                candidatos.add(endTime);
            }
        }

        List<String> slots = new ArrayList<>();
        for (LocalTime cursor : candidatos) {
            // Para hoje, ignora slots cujo serviço inteiro já teria terminado
            // (permite agendar num slot que começou há pouco mas ainda não terminou)
            if (isHoje && cursor.plusMinutes(duracaoServicos).isBefore(agora)) continue;

            LocalTime fimSlot = cursor.plusMinutes(duracaoServicos);
            boolean ocupado = false;

            for (Agendamento ag : agendamentos) {
                int duracaoExist = Math.max(1, ag.getItens().stream()
                        .mapToInt(i -> i.getServico().getDuracaoMinutos() != null ? i.getServico().getDuracaoMinutos() : 0)
                        .sum());
                LocalTime fimExist = ag.getHora().plusMinutes(duracaoExist);

                if (cursor.isBefore(fimExist) && ag.getHora().isBefore(fimSlot)) {
                    ocupado = true;
                    break;
                }
            }

            if (!ocupado) {
                slots.add(cursor.toString().substring(0, 5));
            }
        }

        return slots;
    }

    private List<Servico> carregarServicos(List<Long> ids) {
        List<Servico> servicos = new ArrayList<>();
        for (Long sid : ids) {
            servicos.add(servicoRepository.findById(sid)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado: " + sid)));
        }
        return servicos;
    }

    private BigDecimal calcularValorTotal(List<Servico> servicos) {
        return servicos.stream()
                .map(Servico::getValorServico)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int calcularDuracao(List<Servico> servicos) {
        return Math.max(1, servicos.stream()
                .mapToInt(s -> s.getDuracaoMinutos() != null ? s.getDuracaoMinutos() : 0)
                .sum());
    }

    private void validarDiaAberto(LocalDate data, HorarioFuncionamentoDTO horario) {
        if (horario.diasAbertos() != null && !horario.diasAbertos().isEmpty()
                && !horario.diasAbertos().contains(data.getDayOfWeek().name())) {
            throw new HorarioIndisponivelException("Barbearia fechada neste dia da semana.");
        }
    }

    private void validarDentroDoHorario(LocalDate data, LocalTime hora, HorarioFuncionamentoDTO horario) {
        LocalTime abertura = getAberturaEfetiva(horario, data);
        LocalTime fechamento = getFechamentoEfetivo(horario, data);
        if (hora.isBefore(abertura) || !hora.isBefore(fechamento)) {
            throw new HorarioIndisponivelException(
                    "Horário fora do funcionamento da barbearia ("
                    + abertura.toString().substring(0, 5)
                    + " às " + fechamento.toString().substring(0, 5) + ").");
        }
    }

    private LocalTime getAberturaEfetiva(HorarioFuncionamentoDTO horario, LocalDate data) {
        if (horario.horariosPorDia() != null) {
            var especifico = horario.horariosPorDia().get(data.getDayOfWeek().name());
            if (especifico != null && especifico.abertura() != null) return especifico.abertura();
        }
        return horario.abertura();
    }

    private LocalTime getFechamentoEfetivo(HorarioFuncionamentoDTO horario, LocalDate data) {
        if (horario.horariosPorDia() != null) {
            var especifico = horario.horariosPorDia().get(data.getDayOfWeek().name());
            if (especifico != null && especifico.fechamento() != null) return especifico.fechamento();
        }
        return horario.fechamento();
    }

    private void verificarConflitos(Long barbeiroId, LocalDate data, LocalTime hora, LocalTime fimNovo, Long excluirId) {
        List<Agendamento> agendamentosNoDia = agendamentoRepository.findByBarbeiro_IdAndData(barbeiroId, data);

        for (Agendamento existente : agendamentosNoDia) {
            if (excluirId != null && existente.getId().equals(excluirId)) continue;

            int duracaoExist = Math.max(1, existente.getItens().stream()
                    .mapToInt(i -> i.getServico().getDuracaoMinutos() != null ? i.getServico().getDuracaoMinutos() : 0)
                    .sum());
            LocalTime fimExist = existente.getHora().plusMinutes(duracaoExist);

            if (hora.isBefore(fimExist) && existente.getHora().isBefore(fimNovo)) {
                throw new HorarioIndisponivelException(
                        "Horário indisponível: barbeiro ocupado das "
                        + existente.getHora().toString().substring(0, 5)
                        + " às " + fimExist.toString().substring(0, 5));
            }
        }
    }
}
