package com.br.financas.dto;

import java.time.LocalDate;

public record ClienteDto(Integer Id, String nome, String endereco, String phone , String cpf, LocalDate dataNascimento) {
}
