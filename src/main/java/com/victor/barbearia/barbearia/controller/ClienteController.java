package com.victor.barbearia.barbearia.controller;


import com.victor.barbearia.barbearia.domain.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.victor.barbearia.barbearia.service.ClienteService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> criar (@RequestBody Cliente cliente){
        Cliente criar = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(criar);
    }

    @GetMapping
    public List<Cliente> listar(){
        return clienteService.listar();
    }


}
