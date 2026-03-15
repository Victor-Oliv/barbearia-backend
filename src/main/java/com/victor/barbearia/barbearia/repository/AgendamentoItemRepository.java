package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.AgendamentoItem;
import com.victor.barbearia.barbearia.domain.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoItemRepository extends JpaRepository<AgendamentoItem, Long> {

    boolean existsByServico_IdAndAgendamento_StatusNot(Long servicoId, StatusAgendamento status);

    void deleteByServico_Id(Long servicoId);
}
