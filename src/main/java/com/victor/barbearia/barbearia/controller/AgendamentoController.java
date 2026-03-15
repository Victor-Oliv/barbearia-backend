package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.domain.StatusAgendamento;
import com.victor.barbearia.barbearia.dto.AgendamentoAtualizarRequestDTO;
import com.victor.barbearia.barbearia.dto.AgendamentoRequestDTO;
import com.victor.barbearia.barbearia.dto.AgendamentoResponseDTO;
import com.victor.barbearia.barbearia.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.criar(dto));
    }

    @GetMapping
    public List<AgendamentoResponseDTO> listar() {
        return agendamentoService.listar();
    }

    @GetMapping("/{id}")
    public AgendamentoResponseDTO buscarPorId(@PathVariable Long id) {
        return agendamentoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AgendamentoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody AgendamentoAtualizarRequestDTO dto) {
        return agendamentoService.atualizar(id, dto);
    }

    @PatchMapping("/{id}/status")
    public AgendamentoResponseDTO atualizarStatus(@PathVariable Long id, @RequestParam StatusAgendamento status) {
        return agendamentoService.atualizarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        agendamentoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<AgendamentoResponseDTO> listarPorCliente(@PathVariable Long clienteId) {
        return agendamentoService.listarPorCliente(clienteId);
    }
}
