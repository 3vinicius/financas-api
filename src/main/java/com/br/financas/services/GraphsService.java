package com.br.financas.services;

import com.br.financas.dto.DateValorGraphDTO;
import com.br.financas.repositorys.ClienteRepository;
import com.br.financas.repositorys.CompraRepository;
import com.br.financas.repositorys.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class GraphsService {

    private final ClienteRepository clienteRepository;
    private final PagamentoRepository pagamentoRepository;
    private final CompraRepository compraRepository;

    public HashMap<String, List<DateValorGraphDTO>> buscarDados() {
        List<DateValorGraphDTO>  totalClientesPorPeriodo =  construirDateValorLongGraphDTO(clienteRepository.buscarTotalClientesPorPeriodo());

         List<DateValorGraphDTO> totalPagamentosPorPeriodo = construirDateValorLongGraphDTO(pagamentoRepository.buscarTotalPagamentosPorPeriodo());
         List<DateValorGraphDTO> totalPagamentosNotCompensadoPorPeriodo = construirDateValorGraphDTO(pagamentoRepository.buscarPagamentosNotCompensadoPorPeriodo());
         List<DateValorGraphDTO> totalPagamentosCompensadoPorPeriodo = construirDateValorGraphDTO(pagamentoRepository.buscarPagamentosCompensadoPorPeriodo());

         List<DateValorGraphDTO>  totalComprasPorPeriodo = construirDateValorLongGraphDTO(compraRepository.buscarTotalComprasPorPeriodo());
         List<DateValorGraphDTO> totalComprasQuitadasPorPeriodo = construirDateValorGraphDTO(compraRepository.buscarComprasQuitadasPorPeriodo());
         List<DateValorGraphDTO> totalComprasNotQuitadasPorPeriodo = construirDateValorGraphDTO(compraRepository.buscarComprasNotQuitadasPorPeriodo());

        HashMap<String, List<DateValorGraphDTO>> dados = new HashMap<>();
        dados.put("Total clientes", totalClientesPorPeriodo);
        dados.put("Total pagamentos", totalPagamentosPorPeriodo);
        dados.put("Total pagamentos não compensados", totalPagamentosNotCompensadoPorPeriodo);
        dados.put("Total pagamentos compensados", totalPagamentosCompensadoPorPeriodo);
        dados.put("Total compras", totalComprasPorPeriodo);
        dados.put("Total compras quitadas", totalComprasQuitadasPorPeriodo);
        dados.put("Total compras não quitadas", totalComprasNotQuitadasPorPeriodo);
        return dados;
    }


    private List<DateValorGraphDTO> construirDateValorLongGraphDTO(List<Object[]> dados) {
        List<DateValorGraphDTO> dateValorGraphDTOS = new ArrayList<>();

        for (Object[] dado : dados) {
            BigDecimal mes = (BigDecimal) dado[0];
            BigDecimal ano = (BigDecimal) dado[1];
            Long valor = (Long) dado[2];

            DateValorGraphDTO dateValorGraphDTO = new DateValorGraphDTO(mes.toString()+"-"+ano.toString() , valor.floatValue());
            dateValorGraphDTOS.add(dateValorGraphDTO);
        }
        return dateValorGraphDTOS;
    }

    private List<DateValorGraphDTO> construirDateValorGraphDTO(List<Object[]> dados) {
        List<DateValorGraphDTO> dateValorGraphDTOS = new ArrayList<>();

        for (Object[] dado : dados) {
            BigDecimal mes = (BigDecimal) dado[0];
            BigDecimal ano = (BigDecimal) dado[1];
            BigDecimal valor = (BigDecimal) dado[2];

            DateValorGraphDTO dateValorGraphDTO = new DateValorGraphDTO(mes.toString()+"-"+ano.toString() , valor.floatValue());
            dateValorGraphDTOS.add(dateValorGraphDTO);
        }
        return dateValorGraphDTOS;
    }

}
