package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Cliente;
import com.victor.barbearia.barbearia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteUserDetailService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));

        return User.builder()
                .username(cliente.getEmail())
                .password(cliente.getSenha())
                .roles("CLIENTE")
                .build();
    }
}
