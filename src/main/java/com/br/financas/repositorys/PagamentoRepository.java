package com.br.financas.repositorys;


import com.br.financas.model.Cliente;
import com.br.financas.model.Pagamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.br.financas.shareds.QuerysGraphs.dashboardQuerys.pagamentosSemana;
import static com.br.financas.shareds.QuerysGraphs.pagamentosQuerys.*;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>, JpaSpecificationExecutor<Pagamento> {
    @EntityGraph(attributePaths = {"idCliente"})
    List<Pagamento> findAllByIdCliente(Cliente cliente);

    @EntityGraph(attributePaths = {"idCliente"})
    List<Pagamento> findAll();

    @Query(nativeQuery = true, value = totalPagamentos)
    List<Object[]> buscarTotalPagamentosPorPeriodo();

    @Query(nativeQuery = true, value = totalPagamentosNotCompensado)
    List<Object[]> buscarPagamentosNotCompensadoPorPeriodo();

    @Query(nativeQuery = true, value = totalPagamentosCompensado)
    List<Object[]> buscarPagamentosCompensadoPorPeriodo();

    @Query(nativeQuery = true, value = pagamentosSemana)
    Object buscarPagamentosSemana();
}