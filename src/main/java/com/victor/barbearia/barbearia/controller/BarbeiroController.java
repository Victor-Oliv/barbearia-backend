package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.dto.BarbeiroRequestDTO;
import com.victor.barbearia.barbearia.dto.BarbeiroResponseDTO;
import com.victor.barbearia.barbearia.service.AgendamentoService;
import com.victor.barbearia.barbearia.service.BarbeiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/barbeiros")
@RequiredArgsConstructor
public class BarbeiroController {

    private final BarbeiroService barbeiroService;
    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<BarbeiroResponseDTO> criar(@Valid @RequestBody BarbeiroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(barbeiroService.salvar(dto));
    }

    @GetMapping
    public List<BarbeiroResponseDTO> listar() {
        return barbeiroService.listar();
    }

    @GetMapping("/{id}")
    public BarbeiroResponseDTO buscarPorId(@PathVariable Long id) {
        return barbeiroService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public BarbeiroResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody BarbeiroRequestDTO dto) {
        return barbeiroService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        barbeiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/horarios-disponiveis")
    public List<String> horariosDisponiveis(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) List<Long> servicosId) {
        return agendamentoService.horariosDisponiveis(id, data, servicosId);
    }
}
