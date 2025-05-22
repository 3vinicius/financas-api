package com.br.financas.shareds;

import com.br.financas.dto.DateValorGraphDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Constrói um objeto DateValorGraphDTO a partir de uma lista de objetos.
     *
     * @param dados Lista de objetos contendo dados para construir o DTO.
     * @return Um objeto DateValorGraphDTO com as datas e valores extraídos da lista de objetos.
     */
    public static DateValorGraphDTO construirDateValorDeListObject(List<Object[]> dados) {
        List<String> dates = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        for (Object[] dado : dados) {
            BigDecimal mes = (BigDecimal) dado[0];
            BigDecimal ano = (BigDecimal) dado[1];
            var valor = 0f;
            if (dado[2] instanceof Long result) {
                valor = result.floatValue();
            } else if (dado[2] instanceof BigDecimal result) {
                valor = result.floatValue();
            }
            dates.add(mes.toString()+"-"+ano.toString());
            values.add(valor);

        }
        return new DateValorGraphDTO(dates,values);
    }
}
