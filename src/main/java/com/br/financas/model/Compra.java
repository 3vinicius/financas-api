package com.br.financas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Cliente idCliente;

    @Column(name = "data_prev_pagamento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPrevPagamento;

    @Column(name = "produto", length = Integer.MAX_VALUE)
    private String produto;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "quitado", nullable = false)
    private Boolean quitado =  false;

    @Column(name = "total")
    private BigDecimal total;

}