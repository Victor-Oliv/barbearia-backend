package com.victor.barbearia.barbearia.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServicoRequestDTO(

        @NotBlank(message = "Nome do serviço é obrigatório")
        String nomeServico,

        String descricaoServico,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal valorServico,

        @Min(value = 1, message = "Duração deve ser de pelo menos 1 minuto")
        Integer duracaoMinutos
) {}
