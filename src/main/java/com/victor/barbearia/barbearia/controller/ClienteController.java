package com.victor.barbearia.barbearia.controller;


import com.victor.barbearia.barbearia.domain.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.victor.barbearia.barbearia.service.ClienteService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public Cliente criar (@RequestBody Cliente cliente){
        return clienteService.salvar(cliente);
    }

    @GetMapping
    public List<Cliente> listar(){
        return clienteService.listar();
    }


}
