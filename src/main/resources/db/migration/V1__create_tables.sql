-- =============================================
-- V1 - Criação das tabelas do sistema barbearia
-- Schema: brb
-- =============================================

CREATE SCHEMA IF NOT EXISTS brb;

-- ---------------------------------------------
-- Barbeiro
-- ---------------------------------------------
CREATE TABLE brb.barbeiro (
    id       BIGSERIAL    PRIMARY KEY,
    nome     VARCHAR(255),
    telefone VARCHAR(255),
    email    VARCHAR(255) UNIQUE,
    senha    VARCHAR(255)
);

-- ---------------------------------------------
-- Cliente
-- ---------------------------------------------
CREATE TABLE brb.cliente (
    id       BIGSERIAL    PRIMARY KEY,
    nome     VARCHAR(255),
    telefone VARCHAR(255),
    email    VARCHAR(255) NOT NULL UNIQUE,
    senha    VARCHAR(255) NOT NULL
);

-- ---------------------------------------------
-- Servico
-- ---------------------------------------------
CREATE TABLE brb.servico (
    id                  BIGSERIAL      PRIMARY KEY,
    nome_servico        VARCHAR(255),
    descricao_servico   VARCHAR(255),
    valor_servico       NUMERIC(19, 2),
    duracao_minutos     INTEGER
);

-- ---------------------------------------------
-- Produto
-- ---------------------------------------------
CREATE TABLE brb.produto (
    id       BIGSERIAL      PRIMARY KEY,
    nome     VARCHAR(255)   NOT NULL,
    descricao VARCHAR(255),
    preco    NUMERIC(19, 2) NOT NULL,
    estoque  INTEGER        NOT NULL DEFAULT 0
);

-- ---------------------------------------------
-- Agendamento
-- ---------------------------------------------
CREATE TABLE brb.agendamento (
    id          BIGSERIAL      PRIMARY KEY,
    cliente_id  BIGINT         NOT NULL REFERENCES brb.cliente(id),
    barbeiro_id BIGINT         NOT NULL REFERENCES brb.barbeiro(id),
    data        DATE           NOT NULL,
    hora        TIME           NOT NULL,
    valor_total NUMERIC(19, 2) NOT NULL DEFAULT 0,
    status      VARCHAR(50)
);

-- ---------------------------------------------
-- Agendamento Item
-- ---------------------------------------------
CREATE TABLE brb.agendamento_item (
    id             BIGSERIAL      PRIMARY KEY,
    agendamento_id BIGINT         NOT NULL REFERENCES brb.agendamento(id),
    servico_id     BIGINT         NOT NULL REFERENCES brb.servico(id),
    valor          NUMERIC(19, 2) NOT NULL
);

-- ---------------------------------------------
-- Folga Barbeiro
-- ---------------------------------------------
CREATE TABLE brb.folga_barbeiro (
    id          BIGSERIAL PRIMARY KEY,
    barbeiro_id BIGINT    NOT NULL REFERENCES brb.barbeiro(id),
    data        DATE      NOT NULL
);

-- ---------------------------------------------
-- Horario Funcionamento
-- (id é atribuído manualmente — sem sequence)
-- ---------------------------------------------
CREATE TABLE brb.horario_funcionamento (
    id        BIGINT NOT NULL PRIMARY KEY,
    abertura  TIME   NOT NULL,
    fechamento TIME  NOT NULL
);

-- ElementCollection: dias abertos da semana
CREATE TABLE brb.horario_dias (
    horario_id BIGINT      NOT NULL REFERENCES brb.horario_funcionamento(id),
    dia        VARCHAR(20) NOT NULL
);

-- ElementCollection: horário específico por dia
CREATE TABLE brb.horario_dia_especifico (
    horario_id    BIGINT      NOT NULL REFERENCES brb.horario_funcionamento(id),
    dia           VARCHAR(20) NOT NULL,
    abertura_dia  TIME,
    fechamento_dia TIME,
    PRIMARY KEY (horario_id, dia)
);
