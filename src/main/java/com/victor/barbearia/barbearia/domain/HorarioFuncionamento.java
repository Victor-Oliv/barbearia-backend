package com.victor.barbearia.barbearia.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "horario_funcionamento")
@Getter
@Setter
public class HorarioFuncionamento {

    @Id
    private Long id;

    @Column(name = "abertura", nullable = false)
    private LocalTime abertura;

    @Column(name = "fechamento", nullable = false)
    private LocalTime fechamento;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "horario_dias", joinColumns = @JoinColumn(name = "horario_id"))
    @Column(name = "dia", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> diasAbertos = EnumSet.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
    );

    /** Horários específicos por dia — sobrescreve abertura/fechamento global para o dia indicado */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "horario_dia_especifico", joinColumns = @JoinColumn(name = "horario_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "dia")
    private Map<DayOfWeek, HorarioDia> horariosPorDia = new HashMap<>();
}
