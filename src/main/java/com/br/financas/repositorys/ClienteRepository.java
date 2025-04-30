package com.br.financas.repositorys;


import com.br.financas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.br.financas.shareds.QuerysGraphs.clienteQuerys.totalClientes;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> , JpaSpecificationExecutor<Cliente> {

    boolean existsByCpf(String cpf);


    @Query(value = totalClientes, nativeQuery = true)
    List<Object[]> buscarTotalClientesPorPeriodo();
}