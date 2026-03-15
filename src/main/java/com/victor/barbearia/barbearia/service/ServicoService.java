package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Servico;
import com.victor.barbearia.barbearia.domain.StatusAgendamento;
import com.victor.barbearia.barbearia.dto.ServicoRequestDTO;
import com.victor.barbearia.barbearia.dto.ServicoResponseDTO;
import com.victor.barbearia.barbearia.exception.RecursoNaoEncontradoException;
import com.victor.barbearia.barbearia.repository.AgendamentoItemRepository;
import com.victor.barbearia.barbearia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final AgendamentoItemRepository agendamentoItemRepository;

    @Transactional
    public ServicoResponseDTO salvar(ServicoRequestDTO dto) {
        Servico servico = new Servico();
        servico.setNomeServico(dto.nomeServico());
        servico.setDescricaoServico(dto.descricaoServico());
        servico.setValorServico(dto.valorServico());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        return ServicoResponseDTO.from(servicoRepository.save(servico));
    }

    @Transactional(readOnly = true)
    public List<ServicoResponseDTO> listar() {
        return servicoRepository.findAll()
                .stream()
                .map(ServicoResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado: " + id));
        return ServicoResponseDTO.from(servico);
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado: " + id));
        servico.setNomeServico(dto.nomeServico());
        servico.setDescricaoServico(dto.descricaoServico());
        servico.setValorServico(dto.valorServico());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        return ServicoResponseDTO.from(servicoRepository.save(servico));
    }

    @Transactional
    public void deletar(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Serviço não encontrado: " + id);
        }

        // Bloqueia exclusão se houver agendamentos ativos (não finalizados) com este serviço
        if (agendamentoItemRepository.existsByServico_IdAndAgendamento_StatusNot(id, StatusAgendamento.FINALIZADO)) {
            throw new IllegalStateException(
                    "Não é possível excluir: serviço possui agendamentos ativos. Finalize-os antes de excluir.");
        }

        // Remove os itens de agendamentos finalizados que referenciam este serviço
        agendamentoItemRepository.deleteByServico_Id(id);

        servicoRepository.deleteById(id);
    }
}
