package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.dto.ClienteRequestDTO;
import com.victor.barbearia.barbearia.dto.ClienteResponseDTO;
import com.victor.barbearia.barbearia.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.salvar(dto));
    }

    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        return clienteService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
