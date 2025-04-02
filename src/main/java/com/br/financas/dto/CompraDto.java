package com.br.financas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record  CompraDto(Integer id, BigDecimal valor, String descricao, Integer idCliente, LocalDate dataPrevPagamento,
                        String produto, LocalDateTime dataCricaco, Boolean quitado, BigDecimal total) {
}
