package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.domain.Servico;
import com.victor.barbearia.barbearia.service.ServicoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public Servico salvar(@RequestBody Servico servico){
        return  servicoService.salvar(servico);
    }

    @GetMapping
    public List<Servico> listar(){
        return servicoService.Listar();
    }


}
