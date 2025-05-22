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

    interface dashboardQuerys {
        String pagamentosSemana = "SELECT " +
                "    SUM(valor) as semana_atual, " +
                "    (SELECT SUM(valor) " +
                "                   FROM pagamentos " +
                "                   WHERE data_pagamento between date_trunc('week', NOW())- interval '7 days' and date_trunc('week', NOW()))  as semana_anterior " +
                "FROM pagamentos " +
                "WHERE data_pagamento between date_trunc('week', NOW()) and date_trunc('week', NOW()) + interval '7 days'  ";

        String comprasSemana = "SELECT " +
                "    * " +
                "from compras where data_criacao between date_trunc('week', NOW())- interval '7 days' and date_trunc('week', NOW()) ";

        String clientesSemana = "SELECT " +
                "    count(*) as total_clientes " +
                "FROM clientes " +
                "WHERE data_criacao between date_trunc('week', NOW())- interval '7 days' and date_trunc('week', NOW()) ";

        String dadosComprasSemana = "SELECT " +
                "    COUNT(*) AS total_compras, " +
                "    SUM(total) AS soma_total " +
                "FROM compras " +
                "WHERE data_criacao BETWEEN date_trunc('week', NOW()) AND date_trunc('week', NOW()) + interval '7 days' ";

        String graficoComprasSemana =  "SELECT " +
                "    EXTRACT(MONTH FROM data_criacao) AS mes, " +
                "    EXTRACT(DAY FROM data_criacao) AS dia, " +
                "    sum(valor) as valor " +
                "from compras where data_criacao between date_trunc('week', NOW())- interval '25 days' and date_trunc('week', NOW()) " +
                "GROUP BY dia,mes " +
                "ORDER BY mes,dia ";
    }
}
