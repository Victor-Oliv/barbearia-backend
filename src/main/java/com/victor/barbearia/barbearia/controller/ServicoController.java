package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.domain.Servico;
import com.victor.barbearia.barbearia.service.ServicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<Servico> salvar(@RequestBody Servico servico){
        Servico criar =  servicoService.salvar(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(criar);
    }

    @GetMapping
    public List<Servico> listar(){
        return servicoService.Listar();
    }


}
