package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.dto.FolgaBarbeiroRequestDTO;
import com.victor.barbearia.barbearia.dto.FolgaBarbeiroResponseDTO;
import com.victor.barbearia.barbearia.service.FolgaBarbeiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbeiros/{barbeiroId}/folgas")
@RequiredArgsConstructor
public class FolgaBarbeiroController {

    private final FolgaBarbeiroService service;

    @PostMapping
    public ResponseEntity<FolgaBarbeiroResponseDTO> registrar(
            @PathVariable Long barbeiroId,
            @Valid @RequestBody FolgaBarbeiroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(barbeiroId, dto));
    }

    @GetMapping
    public List<FolgaBarbeiroResponseDTO> listar(@PathVariable Long barbeiroId) {
        return service.listar(barbeiroId);
    }

    @DeleteMapping("/{folgaId}")
    public ResponseEntity<Void> remover(@PathVariable Long barbeiroId, @PathVariable Long folgaId) {
        service.remover(folgaId);
        return ResponseEntity.noContent().build();
    }
}
