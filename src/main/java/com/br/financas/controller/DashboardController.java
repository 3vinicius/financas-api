package com.br.financas.controller;

import com.br.financas.dto.DashboardDTO;
import com.br.financas.services.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.br.financas.shareds.urls.Dashboard;
import static com.br.financas.shareds.urls.Graphs;

@RestController
@AllArgsConstructor
@RequestMapping(value = Dashboard, produces = "application/json")
public class DashboardController {
    final private DashboardService dashboardService;



    @GetMapping()
    public ResponseEntity<DashboardDTO> buscarInformacoesCompras() {
        return ResponseEntity.ok().body(dashboardService.buscarDadosParaDashboard());
    }
}
