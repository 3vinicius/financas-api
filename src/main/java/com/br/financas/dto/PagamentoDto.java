package com.br.financas.dto;

import com.br.financas.model.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoDto(Integer id, Integer idCliente, LocalDate dataPagamento, BigDecimal valor, String descricao) {
}






