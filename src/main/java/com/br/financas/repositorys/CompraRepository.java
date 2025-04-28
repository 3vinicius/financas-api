package com.br.financas.repositorys;

import com.br.financas.model.Cliente;
import com.br.financas.model.Compra;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer>, JpaSpecificationExecutor<Compra> {
    List<Compra> findAllByIdCliente(Cliente idCliente);

    @EntityGraph(attributePaths = {"idCliente"})
    List<Compra> findAll();
}