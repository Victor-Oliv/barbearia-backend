package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiro_IdAndDataAndHora(Long barbeiroId, LocalDate data, LocalTime hora);

    List<Agendamento> findByCliente_Id(Long clienteId);

    List<Agendamento> findByBarbeiro_IdAndData(Long barbeiroId, LocalDate data);
}

