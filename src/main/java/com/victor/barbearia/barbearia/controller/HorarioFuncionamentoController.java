package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.dto.HorarioFuncionamentoDTO;
import com.victor.barbearia.barbearia.service.HorarioFuncionamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracoes/horario")
@RequiredArgsConstructor
public class HorarioFuncionamentoController {

    private final HorarioFuncionamentoService service;

    @GetMapping
    public HorarioFuncionamentoDTO buscar() {
        return service.buscar();
    }

    @PutMapping
    public HorarioFuncionamentoDTO salvar(@Valid @RequestBody HorarioFuncionamentoDTO dto) {
        return service.salvar(dto);
    }
}
