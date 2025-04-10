package com.br.financas.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ClienteDto(Integer id, String nome, String endereco, String phone , String cpf,
                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                         LocalDate dataNascimento) {
}
