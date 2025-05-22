package com.br.financas.services;

import com.br.financas.dto.DateValorGraphDTO;
import com.br.financas.repositorys.ClienteRepository;
import com.br.financas.repositorys.CompraRepository;
import com.br.financas.repositorys.PagamentoRepository;
import com.br.financas.shareds.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class GraphsService {

    private final ClienteRepository clienteRepository;
    private final PagamentoRepository pagamentoRepository;
    private final CompraRepository compraRepository;

    public HashMap<String, DateValorGraphDTO> buscarDados() {
        DateValorGraphDTO  totalClientesPorPeriodo =  Utils.construirDateValorDeListObject(clienteRepository.buscarTotalClientesPorPeriodo());

         DateValorGraphDTO totalPagamentosPorPeriodo = Utils.construirDateValorDeListObject(pagamentoRepository.buscarTotalPagamentosPorPeriodo());
         DateValorGraphDTO totalPagamentosNotCompensadoPorPeriodo = Utils.construirDateValorDeListObject(pagamentoRepository.buscarPagamentosNotCompensadoPorPeriodo());
         DateValorGraphDTO totalPagamentosCompensadoPorPeriodo = Utils.construirDateValorDeListObject(pagamentoRepository.buscarPagamentosCompensadoPorPeriodo());

         DateValorGraphDTO  totalComprasPorPeriodo = Utils.construirDateValorDeListObject(compraRepository.buscarTotalComprasPorPeriodo());
         DateValorGraphDTO totalComprasQuitadasPorPeriodo = Utils.construirDateValorDeListObject(compraRepository.buscarComprasQuitadasPorPeriodo());
         DateValorGraphDTO totalComprasNotQuitadasPorPeriodo = Utils.construirDateValorDeListObject(compraRepository.buscarComprasNotQuitadasPorPeriodo());

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


}
