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

    public HashMap<String, DateValorGraphDTO> buscarDados() {
        DateValorGraphDTO  totalClientesPorPeriodo =  construirDateValorGraphDTO(clienteRepository.buscarTotalClientesPorPeriodo());

         DateValorGraphDTO totalPagamentosPorPeriodo = construirDateValorGraphDTO(pagamentoRepository.buscarTotalPagamentosPorPeriodo());
         DateValorGraphDTO totalPagamentosNotCompensadoPorPeriodo = construirDateValorGraphDTO(pagamentoRepository.buscarPagamentosNotCompensadoPorPeriodo());
         DateValorGraphDTO totalPagamentosCompensadoPorPeriodo = construirDateValorGraphDTO(pagamentoRepository.buscarPagamentosCompensadoPorPeriodo());

         DateValorGraphDTO  totalComprasPorPeriodo = construirDateValorGraphDTO(compraRepository.buscarTotalComprasPorPeriodo());
         DateValorGraphDTO totalComprasQuitadasPorPeriodo = construirDateValorGraphDTO(compraRepository.buscarComprasQuitadasPorPeriodo());
         DateValorGraphDTO totalComprasNotQuitadasPorPeriodo = construirDateValorGraphDTO(compraRepository.buscarComprasNotQuitadasPorPeriodo());

        HashMap<String, DateValorGraphDTO> dados = new HashMap<>();
        dados.put("Total clientes", totalClientesPorPeriodo);
        dados.put("Total pagamentos", totalPagamentosPorPeriodo);
        dados.put("Total pagamentos não compensados", totalPagamentosNotCompensadoPorPeriodo);
        dados.put("Total pagamentos compensados", totalPagamentosCompensadoPorPeriodo);
        dados.put("Total compras", totalComprasPorPeriodo);
        dados.put("Total compras quitadas", totalComprasQuitadasPorPeriodo);
        dados.put("Total compras não quitadas", totalComprasNotQuitadasPorPeriodo);
        return dados;
    }
    

    private DateValorGraphDTO construirDateValorGraphDTO(List<Object[]> dados) {
        List<String> dates = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        for (Object[] dado : dados) {
            BigDecimal mes = (BigDecimal) dado[0];
            BigDecimal ano = (BigDecimal) dado[1];
            var valor = 0f;
            if (dado[2] instanceof Long result) {
                valor = result.floatValue();
            } else if (dado[2] instanceof BigDecimal result) {
                valor = result.floatValue();
            }
            dates.add(mes.toString()+"-"+ano.toString());
            values.add(valor);

        }
        return new DateValorGraphDTO(dates,values);
    }

}
