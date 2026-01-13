package com.victor.barbearia.barbearia.repository;

import com.victor.barbearia.barbearia.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}
