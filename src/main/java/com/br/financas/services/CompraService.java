package com.br.financas.services;


import com.br.financas.dto.CompraClienteDTO;
import com.br.financas.exceptions.ElementNotSearchException;
import com.br.financas.model.Cliente;
import com.br.financas.model.Compra;
import com.br.financas.repositorys.CompraRepository;
import com.br.financas.shareds.GenericSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteService clienteService;


    public List<CompraClienteDTO> buscarCompras() {
        return compraRepository.findAll().stream().map(compra -> new CompraClienteDTO(
                compra.getIdCliente().getId(),
                compra.getIdCliente().getNome(),
                compra.getIdCliente().getEndereco(),
                compra.getIdCliente().getPhone(),
                compra.getIdCliente().getCpf(),
                compra.getIdCliente().getDataNascimento(),
                compra.getId(),
                compra.getValor(),
                compra.getDescricao(),
                compra.getDataPrevPagamento(),
                compra.getProduto(),
                compra.getDataCriacao(),
                compra.getQuitado(),
                compra.getTotal()
        )).toList();
    }

    public Compra buscarCompraPorId(Integer id) {
        return compraRepository.findById(id).orElseThrow(() -> new ElementNotSearchException( "Cliente não encontrado!"));
    }

    public List<Compra> buscarComprasPorCliente(Integer id) {
        return compraRepository.findAllByIdCliente(clienteService.buscarClientePorId(id));
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
        if (dataPrevPagamento != null){
            newCompra.setDataPrevPagamento(dataPrevPagamento);
        }
        if (produto != null && !produto.isEmpty()){
            newCompra.setProduto(produto);
        }
        if (newCompra.getQuitado() == null) {
            newCompra.setQuitado(false);
        }

        return newCompra;
    }
}




