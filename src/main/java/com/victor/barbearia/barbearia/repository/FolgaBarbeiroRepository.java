package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.FolgaBarbeiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FolgaBarbeiroRepository extends JpaRepository<FolgaBarbeiro, Long> {

    boolean existsByBarbeiro_IdAndData(Long barbeiroId, LocalDate data);

    List<FolgaBarbeiro> findByBarbeiro_Id(Long barbeiroId);
}
