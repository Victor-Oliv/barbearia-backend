package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Cliente;
import com.victor.barbearia.barbearia.dto.ClienteRequestDTO;
import com.victor.barbearia.barbearia.dto.ClienteResponseDTO;
import com.victor.barbearia.barbearia.exception.RecursoNaoEncontradoException;
import com.victor.barbearia.barbearia.domain.Agendamento;
import com.victor.barbearia.barbearia.repository.AgendamentoRepository;
import com.victor.barbearia.barbearia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
        if (dto.senha() == null || dto.senha().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória para cadastro.");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());
        cliente.setSenha(passwordEncoder.encode(dto.senha()));

        return ClienteResponseDTO.from(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + id));
        return ClienteResponseDTO.from(cliente);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + id));
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            cliente.setSenha(passwordEncoder.encode(dto.senha()));
        }
        return ClienteResponseDTO.from(clienteRepository.save(cliente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado: " + id);
        }
        // Deletar via entidade para acionar cascade JPA (orphanRemoval dos itens)
        List<Agendamento> agendamentos = agendamentoRepository.findByCliente_Id(id);
        agendamentoRepository.deleteAll(agendamentos);
        clienteRepository.deleteById(id);
    }
}
