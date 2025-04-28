package com.br.financas.services;


import com.br.financas.dto.PagamentoClienteDTO;
import com.br.financas.exceptions.ElementNotSearchException;
import com.br.financas.model.Cliente;
import com.br.financas.model.Pagamento;
import com.br.financas.repositorys.PagamentoRepository;
import com.br.financas.shareds.GenericSpecification;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ClienteService clienteService;

    public List<PagamentoClienteDTO> buscarPagamentos() {
         return pagamentoRepository.findAll().stream().map(pagamento -> new PagamentoClienteDTO(
                pagamento.getIdCliente().getId(),
                pagamento.getIdCliente().getNome(),
                pagamento.getIdCliente().getEndereco(),
                pagamento.getIdCliente().getPhone(),
                pagamento.getIdCliente().getCpf(),
                pagamento.getIdCliente().getDataNascimento(),
                pagamento.getId(),
                pagamento.getDataPagamento(),
                pagamento.getValor(),
                pagamento.getDescricao(),
                pagamento.isCompensado()
            )).toList();

    }

    public Pagamento buscarPagamentoPorId(Integer id) {
        return pagamentoRepository.findById(id).orElseThrow(() -> new ElementNotSearchException( "Pagamento não encontrado!"));
    }

    public List<Pagamento> buscarPagamentosPorCliente(Integer id) {
        return pagamentoRepository.findAllByIdCliente(clienteService.buscarClientePorId(id));
    }

    public List<Pagamento> buscarPagamentosPorData(LocalDate dataInicial, LocalDate dataFinal) {
        Specification<Pagamento> spec = Specification.where(GenericSpecification.<Pagamento>filtroEntrePeriodo("dataPagamento", dataInicial, dataFinal));

        return pagamentoRepository.findAll(spec);
    }

    public Pagamento cadastrarPagamento(BigDecimal valor, String descricao, Integer idCliente ) {

        Pagamento Pagamento = contrutorDePagamentos(valor,descricao,idCliente, Optional.empty());

        return pagamentoRepository.save(Pagamento);
    }

    public void deletarPagamento(Integer Id){
        pagamentoRepository.deleteById(Id);
    }

    public Pagamento atualizarPagamento(Integer id,BigDecimal valor, String descricao, Integer idCliente) {
        Pagamento Pagamento = buscarPagamentoPorId(id);
        Pagamento newPagamento = contrutorDePagamentos(valor,descricao,idCliente, Optional.of(Pagamento));

        return pagamentoRepository.save(newPagamento);
    }

    private Pagamento contrutorDePagamentos(BigDecimal valor, String descricao, Integer idCliente ,
                                      Optional<Pagamento> Pagamento){
        Pagamento newPagamento = new Pagamento();

        if(Pagamento.isPresent()){
            newPagamento = Pagamento.get();
        }
        if (valor != null){
            newPagamento.setValor(valor);
        }
        if (descricao != null && !descricao.isEmpty()){
            newPagamento.setDescricao(descricao);
        }

        return newPagamento;
    }
    
    
}


