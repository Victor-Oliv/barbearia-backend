package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.HorarioDia;
import com.victor.barbearia.barbearia.domain.HorarioFuncionamento;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record HorarioFuncionamentoDTO(
        @NotNull LocalTime abertura,
        @NotNull LocalTime fechamento,
        Set<String> diasAbertos,
        Map<String, HorarioDiaDTO> horariosPorDia
) {
    public record HorarioDiaDTO(LocalTime abertura, LocalTime fechamento) {}

    public static HorarioFuncionamentoDTO from(HorarioFuncionamento h) {
        Set<String> dias = h.getDiasAbertos() == null ? Set.of() :
                h.getDiasAbertos().stream().map(DayOfWeek::name).collect(Collectors.toSet());

        Map<String, HorarioDiaDTO> porDia = new HashMap<>();
        if (h.getHorariosPorDia() != null) {
            h.getHorariosPorDia().forEach((dia, hd) ->
                    porDia.put(dia.name(), new HorarioDiaDTO(hd.getAbertura(), hd.getFechamento())));
        }

        return new HorarioFuncionamentoDTO(h.getAbertura(), h.getFechamento(), dias, porDia);
    }

    /** Dias padrão: segunda a sábado */
    public static Set<String> diasPadrao() {
        return EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)
                .stream().map(DayOfWeek::name).collect(Collectors.toSet());
    }
}
