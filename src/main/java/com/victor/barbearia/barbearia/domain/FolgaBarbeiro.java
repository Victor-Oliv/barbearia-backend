package com.victor.barbearia.barbearia.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "folga_barbeiro")
@Getter
@Setter
public class FolgaBarbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    @Column(name = "data", nullable = false)
    private LocalDate data;
}
