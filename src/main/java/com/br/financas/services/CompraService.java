package com.br.financas.services;


import com.br.financas.exceptions.ElementNotSearchException;
import com.br.financas.model.Compra;
import com.br.financas.repositorys.CompraRepository;
import com.br.financas.shareds.GenericSpecification;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        return compraRepository.findById(id).orElseThrow(() -> new ElementNotSearchException( "Cliente não encontrado!"));
    }

    public List<Compra> buscarComprasPorCliente(Integer id) {
        return compraRepository.findAllByIdcliente(clienteService.buscarClientePorId(id));
    }

    public List<Compra> buscarComprasPorData(LocalDate dataInicial, LocalDate dataFinal) {
        Specification<Compra> spec = Specification.where(GenericSpecification.<Compra>filtroEntrePeriodo("dataCriacao", dataInicial, dataFinal));

        return compraRepository.findAll(spec);
    }

    public Compra cadastrarCompra(BigDecimal valor, String descricao, Integer idCliente ,
                                  LocalDate dataPrevPagamento, String produto) {

        Compra compra = contrutorDeCompras(valor,descricao,idCliente, dataPrevPagamento, produto, Optional.empty());
        compra.setTotal(compra.getValor().negate());

        return compraRepository.save(compra);
    }

    public void deletarCompra(Integer Id){
        compraRepository.deleteById(Id);
    }

    public Compra atualizarCompra(Integer id,BigDecimal valor, String descricao, Integer idCliente ,
                                  LocalDate dataPrevPagamento, String produto) {
        Compra compra = buscarCompraPorId(id);
        Compra newCompra = contrutorDeCompras(valor,descricao,idCliente, dataPrevPagamento, produto, Optional.of(compra));

        return compraRepository.save(newCompra);
    }

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




