package com.br.financas.shareds;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Locale;

public class  GenericSpecification<T> {

    public static <T> Specification<T> filtroLikeString(String campo, String valor) {
        return (root, query, criteriaBuilder) -> {
            if (valor == null || valor.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(campo)), "%" + valor.toUpperCase(Locale.ROOT) + "%");
        };
    }

    public static <T> Specification<T> filtroPorNumero(String campo, Number valor) {
        return (root, query, criteriaBuilder) -> {
            if (valor == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(campo), valor);
        };
    }

    public static <T> Specification<T> filtroPorString(String campo, Number valor) {
        return (root, query, criteriaBuilder) -> {
            if (valor == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(campo), valor);
        };
    }


    public static <T> Specification<T> filtroPorData(String campo, LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(campo), date);
        };
    }



    public static <T> Specification<T> filtroEntrePeriodo(String campo, LocalDate valor1, LocalDate valor2) {
        return (root, query, criteriaBuilder) -> {
            if (valor1 == null || valor2 == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get(campo), valor1, valor2);
        };
    }

}
