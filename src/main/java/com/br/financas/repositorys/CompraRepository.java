package com.br.financas.repositorys;

import com.br.financas.model.Cliente;
import com.br.financas.model.Compra;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.br.financas.shareds.QuerysGraphs.compraQuerys.*;
import static com.br.financas.shareds.QuerysGraphs.dashboardQuerys.*;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer>, JpaSpecificationExecutor<Compra> {
    List<Compra> findAllByIdCliente(Cliente idCliente);

    @EntityGraph(attributePaths = {"idCliente"})
    List<Compra> findAll();

    @Query(nativeQuery = true, value = totalCompras)
    List<Object[]> buscarTotalComprasPorPeriodo();

    @Query(nativeQuery = true, value = totalComprasQuitada)
    List<Object[]> buscarComprasQuitadasPorPeriodo();

    @Query(nativeQuery = true, value = totalComprasNotQuitada)
    List<Object[]> buscarComprasNotQuitadasPorPeriodo();

    @Query(nativeQuery = true, value = comprasSemana)
    List<Compra> buscarComprasDaSemana();

    @Query(nativeQuery = true, value = dadosComprasSemana)
    Object buscarDadosDasComprasDaSemana();

    @Query(nativeQuery = true, value = graficoComprasSemana)
    List<Object[]> buscarGraficoDasComprasDaSemana();

}