package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiro_IdAndDataAndHora(Long barbeiroId, LocalDate data, LocalTime hora);
}

