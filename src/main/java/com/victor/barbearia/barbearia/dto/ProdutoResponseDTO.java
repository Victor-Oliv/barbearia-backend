package com.victor.barbearia.barbearia.dto;

import com.victor.barbearia.barbearia.domain.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque
) {

    public static ProdutoResponseDTO from(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque()
        );
    }
}
