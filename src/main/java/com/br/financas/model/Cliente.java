package com.br.financas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Cliente {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "endereco", length = Integer.MAX_VALUE)
    private String endereco;

    @Column(name = "phone", length = Integer.MAX_VALUE)
    private String phone;

    @Column(name = "cpf", length = Integer.MAX_VALUE)
    private String cpf;

    @Column(name = "data_nascimento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column(name = "data_criacao")
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCriacao;

}