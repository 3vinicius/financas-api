create database financeiro;

SET TIME ZONE 'America/Sao_Paulo';

create table usuarios
(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome text,
    login text,
    password text
);

create table clientes
(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome text,
    endereco text,
    phone text,
    cpf text,
    data_nascimento DATE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


create table pagamentos
(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data_pagamento timestamp default current_timestamp,
    valor numeric,
    descricao text,
    idCliente integer,
    compensado boolean default false,
    constraint fk_cliente foreign key (idCliente) references clientes(id)
);


create table compras
(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    valor numeric,
    descricao text,
    idCliente integer,
    data_prev_pagamento timestamp,
    produto text,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quitado boolean default false,
    total numeric,
    constraint fk_cliente foreign key (idCliente) references clientes(id)
);


-- --------------------------- **DEFAULT DATA TESTES** ----------------------------------------------------


insert into usuarios (nome,login,password) VALUES ('vini','vini','vini');

insert into clientes (nome, endereco, phone, cpf, data_nascimento)
VALUES ('Jose Mariano', 'rua teste', '(82) 9 99573343', '104.965.934-43','1999-08-21');

insert into compras (valor, descricao, idCliente, data_prev_pagamento, produto, quitado, total)
VALUES (120.02, 'compras de roupas', 2, '2025-05-01', 'roupas', false, -120.02);

insert into pagamentos (valor, descricao, idCliente)
values (100, 'Pagamente realizado avista pelo filho do cliente', 2);

update compras set total = total + COALESCE((select SUM(valor) from pagamentos where idCliente = 2 and compensado = false),0),
                   quitado = case when total >= 0 then true else false end
where idCliente = 2 and quitado =  false;
update pagamentos set compensado = true where idCliente = 2;



-- Inserir clientes
insert into clientes (nome, endereco, phone, cpf, data_nascimento)
VALUES
    ('Jose Mariano', 'Rua Teste, 123', '(82) 9 99573343', '104.965.934-43', '1999-08-21'),
    ('Maria Silva', 'Av. Brasil, 456', '(82) 9 99998888', '105.234.876-54', '1985-05-14'),
    ('Carlos Oliveira', 'Rua das Flores, 789', '(82) 9 99887766', '103.765.432-21', '1992-12-02'),
    ('Ana Souza', 'Rua Paraíso, 101', '(82) 9 99665544', '101.234.567-89', '1990-07-30');

-- Inserir compras
insert into compras (valor, descricao, idCliente, data_prev_pagamento, produto, quitado, total)
VALUES
    (200.50, 'Compra de eletrônico', 3, '2025-05-10', 'TV', false, -200.50),
    (50.00, 'Compra de livros', 4, '2025-06-01', 'Livros', false, -50.00),
    (120.02, 'Compra de roupas', 5, '2025-07-01', 'Roupas', false, -120.02),
    (15.99, 'Compra de brinquedo', 6, '2025-05-15', 'Brinquedo', false, -15.99);

-- Inserir pagamentos
insert into pagamentos (valor, descricao, idCliente)
values
    (100.00, 'Pagamento realizado avista pela Maria Silva', 3),
    (50.00, 'Pagamento parcial avista Carlos Oliveira', 4),
    (120.02, 'Pagamento integral pelo filho de Ana Souza', 5);

-- Atualizar total de compras e estado de quitado
update compras
set total = total + COALESCE((select SUM(valor) from pagamentos where idCliente = compras.idCliente and compensado = false), 0),
    quitado = case when total >= 0 then true else false end
where quitado = false;

-- Atualizar compensação dos pagamentos
update pagamentos
set compensado = true
where idCliente in (3, 4, 5);


-- --------------------------- **DEFAULT DATA TESTES** ----------------------------------------------------

INSERT INTO clientes (nome, endereco, phone, cpf, data_nascimento, data_criacao)
SELECT
    'Cliente ' || i,
    'Endereço ' || i,
    '99999-000' || i,
    '000.000.000-' || lpad(i::text, 2, '0'),
    date '1980-01-01' + (random() * 15000)::int, -- nascimento aleatório até hoje
    now() + interval '365 days' - (random() * interval '730 days') -- dates de criação até 1 ano atrás
FROM generate_series(1, 1000) AS s(i);



INSERT INTO pagamentos (valor, descricao, idCliente, compensado, data_pagamento)
SELECT
    round((random() * 900 + 100)::numeric, 2), -- valor entre 100 e 1000
    'Pagamento gerado',
    (SELECT id FROM clientes ORDER BY random() LIMIT 1),
    random() > 0.5,
    now() + interval '365 days' - (random() * interval '730 days')-- pagamento nos últimos 6 meses
FROM generate_series(1, 5000);



INSERT INTO compras (valor, descricao, idCliente, data_prev_pagamento, produto, data_criacao, quitado, total)
SELECT
    round((random() * 450 + 50)::numeric, 2),
    'Compra simulada',
    (SELECT id FROM clientes ORDER BY random() LIMIT 1),
    now() + (random() * interval '60 days'), -- previsão futura até 2 meses
    'Produto ' || i,
    now() + interval '365 days' - (random() * interval '730 days') ,-- dates de criação aleatória
    random() > 0.3, -- 70% quitado
    round((random() * 450 + 50)::numeric, 2)
FROM generate_series(1, 3000) AS s(i);

drop table compras;
-- --------------------------- **CONSULTAS PARA GRAFICOS** ----------------------------------------------------

-- Total de clientes por mês
SELECT
    EXTRACT(YEAR FROM data_criacao) AS ano,
    EXTRACT(MONTH FROM data_criacao) AS mes,

    COUNT(*) AS total_clientes
FROM clientes
GROUP BY ano,mes
ORDER BY ano,mes;

-- Total Pagamentos por mês
SELECT
    EXTRACT(YEAR FROM data_pagamento) AS ano,
    EXTRACT(MONTH FROM data_pagamento) AS mes,
    SUM(valor) AS total_pagamentos
FROM pagamentos where compensado = true
GROUP BY ano,mes
ORDER BY ano,mes;

SELECT
    EXTRACT(YEAR FROM data_pagamento) AS ano,
    EXTRACT(MONTH FROM data_pagamento) AS mes,
    SUM(valor) AS total_pagamentos
FROM pagamentos where compensado = false
GROUP BY ano,mes
ORDER BY ano,mes;


-- Total Compras por mês
SELECT
    EXTRACT(YEAR FROM data_criacao) AS ano,
    EXTRACT(MONTH FROM data_criacao) AS mes,
    SUM(valor) AS total_compras
FROM compras where quitado = false
GROUP BY ano,mes
ORDER BY ano,mes;

SELECT
    EXTRACT(YEAR FROM data_criacao) AS ano,
    EXTRACT(MONTH FROM data_criacao) AS mes,
    SUM(valor) AS total_compras
FROM compras where quitado = true
GROUP BY ano,mes
ORDER BY ano,mes;


-- Pagamento e Compras
SELECT
    EXTRACT(YEAR FROM data_criacao) AS ano,
    EXTRACT(MONTH FROM data_criacao) AS mes,
    SUM(valor) AS total_compras
FROM compras As c
GROUP BY ano,mes
ORDER BY ano,mes;

SELECT
    EXTRACT(YEAR FROM data_pagamento) AS ano,
    EXTRACT(MONTH FROM data_pagamento) AS mes,
    SUM(valor) AS total_pagamentos
FROM pagamentos
GROUP BY ano,mes
ORDER BY ano,mes;

SELECT
                    EXTRACT(YEAR FROM data_compra) AS ano,
                    EXTRACT(MONTH FROM data_compra) AS mes,
                    COUNT(*) AS total_compras
                FROM compras
                GROUP BY ano,mes
                ORDER BY ano,mes ;

-- Querys para dashboard

-- Querys para compras
SELECT
    COUNT(*) AS total_compras,
    SUM(total) AS soma_total
FROM compras
WHERE data_criacao BETWEEN date_trunc('week', NOW()) AND date_trunc('week', NOW()) + interval '7 days';


-- Querys para pagamentos

SELECT
    SUM(valor) as semana_atual,
    (SELECT SUM(valor)
                   FROM pagamentos
                   WHERE data_pagamento between date_trunc('week', NOW()) - interval '7 days' and date_trunc('week', NOW()))  as semana_anterior
FROM pagamentos
WHERE data_pagamento between date_trunc('week', NOW()) and date_trunc('week', NOW()) + interval '7 days';


SELECT
    count(*) as total_clientes
FROM clientes
WHERE data_criacao between date_trunc('week', NOW()) - interval '7 days' and date_trunc('week', NOW());

SELECT
    *
from compras where data_criacao between date_trunc('week', NOW())- interval '7 days' and date_trunc('week', NOW());


SELECT
    sum(valor) as valor,
    EXTRACT(MONTH FROM data_criacao) AS mes,
    EXTRACT(DAY FROM data_criacao) AS dia
from compras where data_criacao between date_trunc('week', NOW())- interval '25 days' and date_trunc('week', NOW())
GROUP BY dia,mes
ORDER BY mes,dia;


