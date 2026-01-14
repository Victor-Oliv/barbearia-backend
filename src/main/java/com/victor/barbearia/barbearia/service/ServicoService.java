package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Servico;
import com.victor.barbearia.barbearia.repository.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    public List<Servico> Listar() {
        return servicoRepository.findAll();
    }

}
