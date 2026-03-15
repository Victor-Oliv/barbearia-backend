package com.victor.barbearia.barbearia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BarbeiroRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        String telefone,

        @Email(message = "E-mail inválido")
        String email,

        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha
) {}
