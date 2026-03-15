package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.domain.FolgaBarbeiro;
import com.victor.barbearia.barbearia.dto.FolgaBarbeiroRequestDTO;
import com.victor.barbearia.barbearia.dto.FolgaBarbeiroResponseDTO;
import com.victor.barbearia.barbearia.exception.RecursoNaoEncontradoException;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import com.victor.barbearia.barbearia.repository.FolgaBarbeiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolgaBarbeiroService {

    private final FolgaBarbeiroRepository folgaRepository;
    private final BarbeiroRepository barbeiroRepository;

    @Transactional
    public FolgaBarbeiroResponseDTO registrar(Long barbeiroId, FolgaBarbeiroRequestDTO dto) {
        Barbeiro barbeiro = barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Barbeiro não encontrado: " + barbeiroId));

        if (folgaRepository.existsByBarbeiro_IdAndData(barbeiroId, dto.data())) {
            throw new IllegalArgumentException("Já existe uma folga registrada para este barbeiro nesta data.");
        }

        FolgaBarbeiro folga = new FolgaBarbeiro();
        folga.setBarbeiro(barbeiro);
        folga.setData(dto.data());
        return FolgaBarbeiroResponseDTO.from(folgaRepository.save(folga));
    }

    @Transactional(readOnly = true)
    public List<FolgaBarbeiroResponseDTO> listar(Long barbeiroId) {
        if (!barbeiroRepository.existsById(barbeiroId)) {
            throw new RecursoNaoEncontradoException("Barbeiro não encontrado: " + barbeiroId);
        }
        return folgaRepository.findByBarbeiro_Id(barbeiroId)
                .stream()
                .map(FolgaBarbeiroResponseDTO::from)
                .toList();
    }

    @Transactional
    public void remover(Long folgaId) {
        if (!folgaRepository.existsById(folgaId)) {
            throw new RecursoNaoEncontradoException("Folga não encontrada: " + folgaId);
        }
        folgaRepository.deleteById(folgaId);
    }
}
