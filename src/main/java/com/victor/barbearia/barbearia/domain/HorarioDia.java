package com.victor.barbearia.barbearia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
public class HorarioDia {

    @Column(name = "abertura_dia")
    private LocalTime abertura;

    @Column(name = "fechamento_dia")
    private LocalTime fechamento;
}
