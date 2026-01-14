package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.service.BarbeiroService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    @PostMapping
    public Barbeiro criar (@RequestBody Barbeiro barbeiro){
        return barbeiroService.salvar(barbeiro);
    }

    @GetMapping
    public List<Barbeiro> listar(){
        return barbeiroService.listar();
    }
}
