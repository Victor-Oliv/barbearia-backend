package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.dto.BarbeiroRequestDTO;
import com.victor.barbearia.barbearia.dto.BarbeiroResponseDTO;
import com.victor.barbearia.barbearia.exception.RecursoNaoEncontradoException;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarbeiroService {

    private final BarbeiroRepository barbeiroRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BarbeiroResponseDTO salvar(BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome(dto.nome());
        barbeiro.setTelefone(dto.telefone());
        barbeiro.setEmail(dto.email());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            barbeiro.setSenha(passwordEncoder.encode(dto.senha()));
        }
        return BarbeiroResponseDTO.from(barbeiroRepository.save(barbeiro));
    }

    @Transactional(readOnly = true)
    public List<BarbeiroResponseDTO> listar() {
        return barbeiroRepository.findAll()
                .stream()
                .map(BarbeiroResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BarbeiroResponseDTO buscarPorId(Long id) {
        Barbeiro barbeiro = barbeiroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Barbeiro não encontrado: " + id));
        return BarbeiroResponseDTO.from(barbeiro);
    }

    @Transactional
    public BarbeiroResponseDTO atualizar(Long id, BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = barbeiroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Barbeiro não encontrado: " + id));
        barbeiro.setNome(dto.nome());
        barbeiro.setTelefone(dto.telefone());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            barbeiro.setSenha(passwordEncoder.encode(dto.senha()));
        }
        return BarbeiroResponseDTO.from(barbeiroRepository.save(barbeiro));
    }

    @Transactional
    public void deletar(Long id) {
        if (!barbeiroRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Barbeiro não encontrado: " + id);
        }
        barbeiroRepository.deleteById(id);
    }
}
