package com.br.financas.controller;

import com.br.financas.services.JasperReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.br.financas.shareds.urls.relatorios.*;

@RestController
@AllArgsConstructor
@RequestMapping(Url_Relatorios)
public class RelatoriosController {

    private final JasperReportService jasperReportService;


    @GetMapping()
    public ResponseEntity<byte[]> gerarRelatorio() throws JRException, IOException {
        return jasperReportService.relatorioClientes();
    }

}
