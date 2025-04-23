package com.br.financas.controller;

import com.br.financas.services.JasperReportService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
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
    public void gerarRelatorio() throws JRException, IOException {
        jasperReportService.relatorioClientes();
    }

}
