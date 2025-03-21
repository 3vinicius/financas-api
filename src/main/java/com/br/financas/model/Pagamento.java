package com.br.financas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagamentos")
public class Pagamento {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente")
    private Cliente idcliente;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "descricao", length = Integer.MAX_VALUE)
    private String descricao;

}