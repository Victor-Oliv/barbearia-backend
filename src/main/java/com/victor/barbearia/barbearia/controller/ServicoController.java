package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.dto.ServicoRequestDTO;
import com.victor.barbearia.barbearia.dto.ServicoResponseDTO;
import com.victor.barbearia.barbearia.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> salvar(@Valid @RequestBody ServicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.salvar(dto));
    }

    @GetMapping
    public List<ServicoResponseDTO> listar() {
        return servicoService.listar();
    }

    @GetMapping("/{id}")
    public ServicoResponseDTO buscarPorId(@PathVariable Long id) {
        return servicoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ServicoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ServicoRequestDTO dto) {
        return servicoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
