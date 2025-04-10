package com.br.financas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record  CompraDto(Integer id, BigDecimal valor, String descricao, Integer idCliente,
                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                         LocalDate dataPrevPagamento,
                         String produto,
                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                         LocalDateTime dataCricaco, Boolean quitado, BigDecimal total) {
}
