package com.br.financas.dto;

import com.br.financas.model.Compra;

import java.util.HashMap;
import java.util.List;

public record DashboardDTO (List<HashMap<String, Float>> dashboard, List<Compra> listaCompra, Integer qtnClientesSemana, DateValorGraphDTO dateValorGraphDTO) {}
