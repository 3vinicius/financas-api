package com.br.financas.services;


import com.br.financas.model.Cliente;
import com.br.financas.model.Compra;
import com.br.financas.repositorys.CompraRepository;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteService clienteService;

    public List<Compra> buscarCompras() {
        return compraRepository.findAll();
    }

    public Compra buscarCompraPorId(Integer id) {
        return compraRepository.findById(id).get();
    }

    public List<Compra> buscarComprasPorCliente(Integer id) {
        return compraRepository.findAllByIdcliente(clienteService.buscarClientePorId(id));
    }

    public Compra cadastrarCompra(BigDecimal valor, String descricao, Integer idCliente ,
                                  LocalDate dataPrevPagamento, String produto) {


        Compra compra = contrutorDeCompras(valor,descricao,idCliente, dataPrevPagamento, produto, Optional.empty());
        compra.setTotal(compra.getValor().negate());

        return compraRepository.save(compra);
    }

    public void deletarCompra(Integer Id){
        try {
            compraRepository.deleteById(Id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada!");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar compra: " + e.getMessage());
        }
    }

   // public Compra atualizarCompra(Compra compra) {}

    private Compra contrutorDeCompras(BigDecimal valor, String descricao, Integer idCliente ,
                                       LocalDate dataPrevPagamento, String produto, Optional<Compra> compra){
        Compra newCompra = new Compra();

        if(compra.isPresent()){
            newCompra = compra.get();
        }
        if (valor != null){
            newCompra.setValor(valor);
        }
        if (descricao != null && !descricao.isEmpty()){
            newCompra.setDescricao(descricao);
        }
        if (idCliente != null){
            newCompra.setIdcliente(clienteService.buscarClientePorId(idCliente));
        }
        if (dataPrevPagamento != null){
            newCompra.setDataPrevPagamento(dataPrevPagamento);
        }
        if (produto != null && !produto.isEmpty()){
            newCompra.setProduto(produto);
        }

        return newCompra;
    }
}




