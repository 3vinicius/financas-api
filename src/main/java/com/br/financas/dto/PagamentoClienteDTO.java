package com.br.financas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record   PagamentoClienteDTO(Integer idCliente, String nome, String endereco, String phone , String cpf,
                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                                  LocalDate dataNascimento,
                                  Integer id,
                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                                  LocalDate dataPagamento, BigDecimal valor, String descricao, Boolean compensado

) {
}
