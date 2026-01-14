package com.victor.barbearia.barbearia.controller;


import com.victor.barbearia.barbearia.domain.Agendamento;
import com.victor.barbearia.barbearia.service.AgendamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> criar(@RequestBody Agendamento agendamento){
        Agendamento criar = agendamentoService.criar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(criar);
    }
}
