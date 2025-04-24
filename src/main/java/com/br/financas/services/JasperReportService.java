package com.br.financas.services;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    public ResponseEntity<byte[]> relatorioClientes() throws IOException, JRException {
        DataSource dataSource = new DriverManagerDataSource(url, username, password);
        try (InputStream inputStream = resource.getInputStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource.getConnection());
            this.criarDiretorio();
            JasperExportManager.exportReportToPdfFile(print, "pdf/clientesRelatorio.pdf");
            return this.contruirResponseFile("clientesRelatorio.pdf");
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

    public ResponseEntity<byte[]> contruirResponseFile(String nome) throws IOException, JRException {

          File pdfFile = new File("pdf/"+nome);

          if (pdfFile.exists()) {

              byte[] pdfContent = java.nio.file.Files.readAllBytes(pdfFile.toPath());

              HttpHeaders headers = new HttpHeaders();
              headers.setContentType(MediaType.APPLICATION_PDF);
              headers.setContentDisposition(ContentDisposition.builder("attachment")
                      .filename("clientesRelatorio.pdf")
                      .build());

              return ResponseEntity.ok()
                      .headers(headers)
                      .body(pdfContent);
          } else {
              return ResponseEntity.status(HttpStatus.NOT_FOUND)
                      .body(null);
          }
    }

}
