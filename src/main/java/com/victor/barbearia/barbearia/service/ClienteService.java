package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.victor.barbearia.barbearia.repository.ClienteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar (Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

}
