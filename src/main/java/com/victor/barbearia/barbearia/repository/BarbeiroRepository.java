package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {
    Optional<Barbeiro> findByEmail(String email);
}
