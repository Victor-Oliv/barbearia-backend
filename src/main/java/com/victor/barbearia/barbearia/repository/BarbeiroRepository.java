package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {
}
