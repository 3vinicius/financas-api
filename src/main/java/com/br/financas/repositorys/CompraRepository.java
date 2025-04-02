package com.br.financas.repositorys;

import com.br.financas.model.Cliente;
import com.br.financas.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findAllByIdcliente(Cliente cliente);
}