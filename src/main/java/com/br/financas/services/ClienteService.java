package com.br.financas.services;

import com.br.financas.dto.ClienteNomeIdDTO;
import com.br.financas.exceptions.ElementNotSearchException;
import com.br.financas.model.Cliente;
import com.br.financas.repositorys.ClienteRepository;
import com.br.financas.shareds.GenericSpecification;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        return clienteRepository.findById(id).orElseThrow(() -> new ElementNotSearchException( "Cliente não encontrado!"));
    }

    public List<Cliente> buscarClientePorNomeOuCpf(String nome, String cpf){

        Specification<Cliente> spec = Specification.where(GenericSpecification.<Cliente>filtroLikeString("nome", nome))
                .and(GenericSpecification.<Cliente>filtroLikeString("cpf",cpf));

        return clienteRepository.findAll(spec);
    }

    public Cliente cadastrarCliente(String nome,String enderco, String phone ,String cpf, LocalDate dataNascimento){
        if(clienteRepository.existsByCpf(cpf)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"CPF já cadastrado!");
        }
        return clienteRepository.save(contrutorDeClientes(nome,enderco,phone,cpf,dataNascimento,Optional.empty()));
    }

    public Cliente atualizarCliente(Integer id, String nome,String enderco, String phone ,String cpf, LocalDate dataNascimento){
        Cliente cliente = buscarClientePorId(id);
        return clienteRepository.save(contrutorDeClientes(nome,enderco,phone,cpf,dataNascimento,Optional.of(cliente)));
    }

    public void deletarCliente(Integer id) {
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Cliente vinculado a pagamento ou compra");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<ClienteNomeIdDTO> buscarClienteNomeIdDTO(){
        return clienteRepository.findAll().stream().map(cliente -> new ClienteNomeIdDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf()
        )).toList();
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
