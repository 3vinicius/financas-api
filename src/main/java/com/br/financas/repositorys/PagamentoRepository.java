package com.br.financas.repositorys;

import com.br.financas.model.Cliente;
import com.br.financas.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>, JpaSpecificationExecutor<Pagamento> {
    List<Pagamento> findAllByIdCliente(Cliente cliente);
}