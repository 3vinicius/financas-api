package com.br.financas.shareds;

public interface QuerysGraphs {


    interface clienteQuerys {
        String totalClientes = "SELECT " +
                "    EXTRACT(YEAR FROM data_criacao) AS ano, " +
                "    EXTRACT(MONTH FROM data_criacao) AS mes, " +
                "    COUNT(*) AS total_clientes " +
                "FROM clientes " +
                "GROUP BY ano,mes " +
                "ORDER BY ano,mes ";
    }

    interface compraQuerys {
        String totalCompras = "SELECT " +
                "    EXTRACT(YEAR FROM data_criacao) AS ano, " +
                "    EXTRACT(MONTH FROM data_criacao) AS mes, " +
                "    COUNT(*) AS total_compras " +
                "FROM compras " +
                "GROUP BY ano,mes " +
                "ORDER BY ano,mes ";
        
        
        String totalComprasNotQuitada = "SELECT " +
                "    EXTRACT(YEAR FROM data_criacao) AS ano, " +
                "    EXTRACT(MONTH FROM data_criacao) AS mes, " +
                "    SUM(valor) AS total_compras " +
                "FROM compras where quitado = false " +
                "GROUP BY ano,mes " +
                "ORDER BY ano,mes ";

        String totalComprasQuitada = "SELECT " +
                "    EXTRACT(YEAR FROM data_criacao) AS ano, " +
                "    EXTRACT(MONTH FROM data_criacao) AS mes, " +
                "    SUM(valor) AS total_compras " +
                "FROM compras where quitado = true " +
                "GROUP BY ano,mes " +
                "ORDER BY ano,mes ";
    }

    interface pagamentosQuerys {
        String totalPagamentos = "SELECT " +
                "    EXTRACT(YEAR FROM data_pagamento) AS ano, " +
                "    EXTRACT(MONTH FROM data_pagamento) AS mes, " +
                "    COUNT(*) AS total_pagamentos " +
                "FROM pagamentos " +
                "GROUP BY ano,mes " +
                "ORDER BY ano,mes ";

        String totalPagamentosNotCompensado = "SELECT " +
                "    EXTRACT(YEAR FROM data_pagamento) AS ano, " +
                "    EXTRACT(MONTH FROM data_pagamento) AS mes, " +
                "    SUM(valor) AS total_pagamentos " +
                "FROM pagamentos where compensado = false " +
                "GROUP BY ano,mes " +
                "ORDER BY ano,mes ";

        String totalPagamentosCompensado = "SELECT " +
                "    EXTRACT(YEAR FROM data_pagamento) AS ano, " +
                "    EXTRACT(MONTH FROM data_pagamento) AS mes, " +
                "    SUM(valor) AS total_pagamentos " +
                "FROM pagamentos where compensado = true " +
                "GROUP BY ano,mes " +
                "ORDER BY ano,mes ";
    }

}
