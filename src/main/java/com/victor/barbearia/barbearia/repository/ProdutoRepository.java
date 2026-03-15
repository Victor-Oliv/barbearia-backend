package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
