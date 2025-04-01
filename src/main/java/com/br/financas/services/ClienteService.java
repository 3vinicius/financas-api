package com.br.financas.services;

import com.br.financas.model.Cliente;
import com.br.financas.repositorys.ClienteRepository;
import com.br.financas.shareds.GenericSpecification;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> buscarClientes(){
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Integer id){
        return clienteRepository.findById(id).get();
    }

    public List<Cliente> buscarClientePorNomeOuCpf(String nome, String cpf){

        Specification<Cliente> spec = Specification.where(GenericSpecification.<Cliente>filtroLikeString("nome", nome))
                .and(GenericSpecification.<Cliente>filtroLikeString("cpf",cpf));

        return clienteRepository.findAll(spec);
    }

    public Cliente cadastrarCliente(String nome,String enderco, String phone ,String cpf, LocalDate dataNascimento){
        if(clienteRepository.existsByCpf(cpf)){
            throw new IllegalArgumentException("CPF já cadastrado!");
        }
        return clienteRepository.save(contrutorDeClientes(nome,enderco,phone,cpf,dataNascimento,Optional.empty()));
    }

    public Cliente atualizarCliente(Integer Id, String nome,String enderco, String phone ,String cpf, LocalDate dataNascimento){

        Optional<Cliente> cliente = clienteRepository.findById(Id);

        if(cliente.isEmpty()){
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        return clienteRepository.save(contrutorDeClientes(nome,enderco,phone,cpf,dataNascimento,cliente));
    }


    public void deletarCliente(Integer id) {
        try {
            clienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar cliente: " + e.getMessage());
        }
    }

    private Cliente contrutorDeClientes(String nome, String enderco, String phone , String cpf, LocalDate dataNascimento, Optional<Cliente> cliente){
        Cliente newCliente = new Cliente();

        if(cliente.isPresent()){
            newCliente = cliente.get();
        }

        if (nome != null && !nome.isEmpty()){
            newCliente.setNome(nome);
        }
        if (enderco != null && !enderco.isEmpty()){
            newCliente.setEndereco(enderco);
        }
        if (phone != null && !phone.isEmpty()){
            newCliente.setPhone(phone);
        }
        if (cpf != null && !cpf.isEmpty()){
            newCliente.setCpf(cpf);
        }
        if (dataNascimento != null){
            newCliente.setDataNascimento(dataNascimento);
        }
        return newCliente;
    }



}
