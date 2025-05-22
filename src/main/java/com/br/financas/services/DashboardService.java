package com.br.financas.services;

import com.br.financas.dto.DashboardDTO;
import com.br.financas.dto.DateValorGraphDTO;
import com.br.financas.model.Compra;
import com.br.financas.repositorys.ClienteRepository;
import com.br.financas.repositorys.CompraRepository;
import com.br.financas.repositorys.PagamentoRepository;
import com.br.financas.shareds.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class DashboardService {

    private final CompraRepository comprasRepository;
    private final ClienteRepository clienteRepository;
    private final PagamentoRepository pagamentoRepository;


    public DashboardDTO  buscarDadosParaDashboard() {
        List<HashMap<String, Float>> listaDados = new ArrayList<>();

        listaDados.add(this.dadosComprasSemana());
        listaDados.add(this.dadosPagamentosSemana());

        return new DashboardDTO(listaDados, this.comprasDaSemana(), this.qtnClientesSemana(), this.graficosComprasSemana());
    }

    private DateValorGraphDTO graficosComprasSemana(){
        return Utils.construirDateValorDeListObject(comprasRepository.buscarGraficoDasComprasDaSemana());
    }

    private HashMap<String, Float> dadosComprasSemana() {
        HashMap<String, Float> map = new HashMap<>();
        Object[] result = (Object[]) comprasRepository.buscarDadosDasComprasDaSemana();

        var quantidadeTotalDeComprasDaSemana = (Long) result[0];
        var valorTotalDeComprasDaSemana = new BigDecimal(0);

        if (result[1] != null) {
            valorTotalDeComprasDaSemana = ((BigDecimal) result[1]).setScale(2, RoundingMode.HALF_UP);
        }

        map.put("quantidadeTotalDeComprasDaSemana", quantidadeTotalDeComprasDaSemana.floatValue());
        map.put("valorTotalDeComprasDaSemana", valorTotalDeComprasDaSemana.floatValue());


        return map;
    }

    private List<Compra> comprasDaSemana() {
       return comprasRepository.buscarComprasDaSemana();
    }

    private Integer qtnClientesSemana() {
        return clienteRepository.buscarClienteDaSemana();
    }

    private  HashMap<String, Float> dadosPagamentosSemana(){
        Object[] result = (Object[]) pagamentoRepository.buscarPagamentosSemana();

        float semanaAtual = 0.0F;
        float semanaAnterior = 0.0F;
        float percetualDeVendasDaSemana = 0.0F;

        HashMap<String, Float> map = new HashMap<>();

        if (result[1] != null & result[0] != null) {
            semanaAtual =((BigDecimal) result[0]).floatValue();
            semanaAnterior = ((BigDecimal) result[1]).floatValue();
            percetualDeVendasDaSemana = ((semanaAtual - semanaAnterior) / semanaAnterior * 100);
        }

        map.put("totalPagamentosSemana", semanaAtual);
        map.put("percentualDeVendaEntreAsSemanas", new BigDecimal(percetualDeVendasDaSemana).setScale(2, RoundingMode.HALF_UP).floatValue());

        return map;
    }
}
