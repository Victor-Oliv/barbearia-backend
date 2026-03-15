package com.victor.barbearia.barbearia.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "servico")
@Getter
@Setter
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeServico;
    private String descricaoServico;
    private BigDecimal valorServico;

    private Integer duracaoMinutos;

}
