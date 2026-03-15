package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.HorarioDia;
import com.victor.barbearia.barbearia.domain.HorarioFuncionamento;
import com.victor.barbearia.barbearia.dto.HorarioFuncionamentoDTO;
import com.victor.barbearia.barbearia.repository.HorarioFuncionamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioFuncionamentoService {

    private static final Long ID_UNICO = 1L;
    private static final LocalTime ABERTURA_PADRAO = LocalTime.of(8, 0);
    private static final LocalTime FECHAMENTO_PADRAO = LocalTime.of(19, 0);

    private final HorarioFuncionamentoRepository repository;

    @Transactional(readOnly = true)
    public HorarioFuncionamentoDTO buscar() {
        return repository.findById(ID_UNICO)
                .map(HorarioFuncionamentoDTO::from)
                .orElse(new HorarioFuncionamentoDTO(ABERTURA_PADRAO, FECHAMENTO_PADRAO,
                        HorarioFuncionamentoDTO.diasPadrao(), Map.of()));
    }

    @Transactional
    public HorarioFuncionamentoDTO salvar(HorarioFuncionamentoDTO dto) {
        if (dto.fechamento().isBefore(dto.abertura()) || dto.fechamento().equals(dto.abertura())) {
            throw new IllegalArgumentException("Horário de fechamento deve ser posterior ao de abertura.");
        }
        HorarioFuncionamento h = repository.findById(ID_UNICO).orElse(new HorarioFuncionamento());
        h.setId(ID_UNICO);
        h.setAbertura(dto.abertura());
        h.setFechamento(dto.fechamento());

        if (dto.diasAbertos() != null) {
            Set<DayOfWeek> dias = dto.diasAbertos().stream()
                    .map(DayOfWeek::valueOf)
                    .collect(Collectors.toSet());
            h.setDiasAbertos(dias.isEmpty() ? EnumSet.noneOf(DayOfWeek.class) : EnumSet.copyOf(dias));
        }

        Map<DayOfWeek, HorarioDia> porDia = new HashMap<>();
        if (dto.horariosPorDia() != null) {
            dto.horariosPorDia().forEach((diaStr, hd) -> {
                if (hd != null && hd.abertura() != null && hd.fechamento() != null) {
                    HorarioDia ent = new HorarioDia();
                    ent.setAbertura(hd.abertura());
                    ent.setFechamento(hd.fechamento());
                    porDia.put(DayOfWeek.valueOf(diaStr), ent);
                }
            });
        }
        h.getHorariosPorDia().clear();
        h.getHorariosPorDia().putAll(porDia);

        return HorarioFuncionamentoDTO.from(repository.save(h));
    }
}
