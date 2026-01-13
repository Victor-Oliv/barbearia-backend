package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BarbeiroService {

    private BarbeiroRepository barbeiroRepository;

    public Barbeiro salvar (Barbeiro barbeiro){
        return barbeiroRepository.save(barbeiro);
    }

    public List<Barbeiro> listar(){
        return barbeiroRepository.findAll();
    }

}
