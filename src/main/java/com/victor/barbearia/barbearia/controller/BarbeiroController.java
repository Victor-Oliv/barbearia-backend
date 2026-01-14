package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.service.BarbeiroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    @PostMapping
    public ResponseEntity<Barbeiro> criar (@RequestBody Barbeiro barbeiro){
        Barbeiro criar = barbeiroService.salvar(barbeiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(criar);
    }

    @GetMapping
    public List<Barbeiro> listar(){
        return barbeiroService.listar();
    }
}
