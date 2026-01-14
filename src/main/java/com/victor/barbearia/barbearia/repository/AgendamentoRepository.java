package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiroIdAndDataHora(Long barbeiroId, LocalDateTime dataHora);
}
