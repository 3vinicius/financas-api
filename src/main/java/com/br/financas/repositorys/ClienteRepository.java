package com.br.financas.repositorys;

import com.br.financas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> , JpaSpecificationExecutor<Cliente> {

    boolean existsByCpf(String cpf);


}