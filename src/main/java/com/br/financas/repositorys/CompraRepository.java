package com.br.financas.repositorys;

import com.br.financas.model.Cliente;
import com.br.financas.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Integer>, JpaSpecificationExecutor<Compra> {
    List<Compra> findAllByIdcliente(Cliente idcliente);
}