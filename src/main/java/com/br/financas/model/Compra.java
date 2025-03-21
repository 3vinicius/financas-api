package com.br.financas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compras")
public class Compra {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "descricao", length = Integer.MAX_VALUE)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente")
    private Cliente idcliente;

    @Column(name = "data_prev_pagamento")
    private Integer dataPrevPagamento;

    @Column(name = "produto", length = Integer.MAX_VALUE)
    private String produto;

    @Column(name = "data_cricaco")
    private Instant dataCricaco;

    @Column(name = "quitado")
    private Boolean quitado;

    @Column(name = "total")
    private BigDecimal total;

}