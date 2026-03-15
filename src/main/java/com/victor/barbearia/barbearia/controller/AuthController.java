package com.victor.barbearia.barbearia.controller;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.domain.Cliente;
import com.victor.barbearia.barbearia.dto.UserInfoDTO;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import com.victor.barbearia.barbearia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> me(Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_CLIENTE")
                .replace("ROLE_", "");

        if (role.equals("BARBEIRO")) {
            Barbeiro barbeiro = barbeiroRepository.findByEmail(email).orElseThrow();
            return ResponseEntity.ok(new UserInfoDTO(barbeiro.getId(), email, "BARBEIRO"));
        }

        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(new UserInfoDTO(cliente.getId(), email, "CLIENTE"));
    }
}
