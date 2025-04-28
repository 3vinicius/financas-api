package com.br.financas.repositorys;

import com.br.financas.model.Cliente;
import com.br.financas.model.Pagamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>, JpaSpecificationExecutor<Pagamento> {
    @EntityGraph(attributePaths = {"idCliente"})
    List<Pagamento> findAllByIdCliente(Cliente cliente);

    @EntityGraph(attributePaths = {"idCliente"})
    List<Pagamento> findAll();
}