package com.victor.barbearia.barbearia.service;

import com.victor.barbearia.barbearia.domain.Produto;
import com.victor.barbearia.barbearia.dto.ProdutoRequestDTO;
import com.victor.barbearia.barbearia.dto.ProdutoResponseDTO;
import com.victor.barbearia.barbearia.exception.RecursoNaoEncontradoException;
import com.victor.barbearia.barbearia.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setEstoque(dto.estoque());
        return ProdutoResponseDTO.from(produtoRepository.save(produto));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
        return ProdutoResponseDTO.from(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setEstoque(dto.estoque());
        return ProdutoResponseDTO.from(produtoRepository.save(produto));
    }

    @Transactional
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
