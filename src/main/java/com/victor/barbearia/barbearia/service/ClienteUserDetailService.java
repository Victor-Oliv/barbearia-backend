package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import com.victor.barbearia.barbearia.domain.Cliente;
import com.victor.barbearia.barbearia.repository.BarbeiroRepository;
import com.victor.barbearia.barbearia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteUserDetailService implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Barbeiro> barbeiro = barbeiroRepository.findByEmail(username);
        if (barbeiro.isPresent() && barbeiro.get().getSenha() != null) {
            return User.builder()
                    .username(barbeiro.get().getEmail())
                    .password(barbeiro.get().getSenha())
                    .roles("BARBEIRO")
                    .build();
        }

        Cliente cliente = clienteRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.builder()
                .username(cliente.getEmail())
                .password(cliente.getSenha())
                .roles("CLIENTE")
                .build();
    }
}
