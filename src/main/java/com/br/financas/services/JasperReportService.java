package com.br.financas.services;

import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JasperReportService {

    private final Resource resource =  new ClassPathResource( "reports/clientesRelatorio.jrxml");

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private DataSource dataSource;



    private DataSource generateConnection() {
        return new DriverManagerDataSource(url, username, password);
    }

    public void relatorioClientes() throws IOException, JRException {
        dataSource = new DriverManagerDataSource(url, username, password);
        try (InputStream inputStream = resource.getInputStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, new HashMap<>(), this.dataSource.getConnection());
            this.criarDiretorio();
            JasperExportManager.exportReportToPdfFile(print, "pdf/clientesRelatorio.pdf");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void criarDiretorio() {
        File diretorio = new File("pdf");
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
    }

//    private void graficosCliente() throws IOException, JRException {
//
//        Resource resource =  new ClassPathResource( "reports/graficosClientes.jrxml");
//        try (InputStream inputStream = resource.getInputStream()) {
//            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
//            JasperPrint print = JasperFillManager.fillReport(jasperReport, new HashMap<>(), this.dataSource.getConnection());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
