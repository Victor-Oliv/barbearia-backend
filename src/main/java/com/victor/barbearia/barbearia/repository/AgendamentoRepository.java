package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiro_IdAndDataHora(Long barbeiroId, LocalDateTime dataHora);
}
