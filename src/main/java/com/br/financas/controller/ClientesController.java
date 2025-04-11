package com.br.financas.controller;


import com.br.financas.dto.ClienteDto;
import com.br.financas.dto.ClienteNomeIdDTO;
import com.br.financas.model.Cliente;
import com.br.financas.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.br.financas.shareds.urls.clientes.*;

@RestController
@AllArgsConstructor
@RequestMapping(Url_Clientes)
public class ClientesController {

    private final ClienteService clienteService;

    @GetMapping("/all")
    public ResponseEntity<List<Cliente>> buscarClientes() {
        return ResponseEntity.ok().body(clienteService.buscarClientes());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarClientePorNomeOuCpf(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "cpf", required = false) String cpf) {
        return ResponseEntity.ok().body(clienteService.buscarClientePorNomeOuCpf(nome, cpf));
    }

    @GetMapping("/all/idName")
    public ResponseEntity<List<ClienteNomeIdDTO>> buscarClienteNomeId() {
        return ResponseEntity.ok().body(clienteService.buscarClienteNomeIdDTO());
    }

    @GetMapping()
    public ResponseEntity<Cliente> buscarClientePorId(@RequestParam(value = "id", required = true) Integer id) {
        return ResponseEntity.ok().body(clienteService.buscarClientePorId(id));
    }

    @PostMapping()
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDto cliente) {
        return ResponseEntity.ok().body(clienteService.cadastrarCliente(cliente.nome(), cliente.endereco(),
                cliente.phone(), cliente.cpf(), cliente.dataNascimento()));
    }

    @PutMapping()
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody ClienteDto cliente) {
        return ResponseEntity.ok().body(clienteService.atualizarCliente(cliente.id(), cliente.nome(),
                cliente.endereco(), cliente.phone(), cliente.cpf(), cliente.dataNascimento()));
    }

    @DeleteMapping()
    public void deletarCliente(@RequestParam Integer id) {
        clienteService.deletarCliente(id);
    }

}
