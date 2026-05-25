-- ============================================================
--  BLOGUINHO — Script de criação do banco de dados MySQL
--  Execute esse arquivo antes de rodar o sistema
-- ============================================================

CREATE DATABASE IF NOT EXISTS bloguinho
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE bloguinho;

-- ------------------------------------------------------------
-- Tabela: usuario
-- Armazena os dados de cada usuário cadastrado
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,   -- email é único no sistema
    senha         VARCHAR(255) NOT NULL,
    data_criacao  DATE         NOT NULL,
    PRIMARY KEY (id)
);

-- ------------------------------------------------------------
-- Tabela: post
-- Cada post pertence a um usuário (autor)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS post (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    titulo        VARCHAR(200)  NOT NULL,
    conteudo      TEXT          NOT NULL,
    dt_criacao    DATE          NOT NULL,
    qtd_curtidas  BIGINT        NOT NULL DEFAULT 0,
    autor_id      BIGINT        NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_post_autor FOREIGN KEY (autor_id) REFERENCES usuario(id)
);

-- ------------------------------------------------------------
-- Tabela: comentario
-- Cada comentário pertence a um post e a um usuário (autor)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS comentario (
    id            BIGINT   NOT NULL AUTO_INCREMENT,
    conteudo      TEXT     NOT NULL,
    data_criacao  DATE     NOT NULL,
    qtd_curtidas  BIGINT   NOT NULL DEFAULT 0,
    autor_id      BIGINT   NOT NULL,
    post_id       BIGINT   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_comentario_autor FOREIGN KEY (autor_id) REFERENCES usuario(id),
    CONSTRAINT fk_comentario_post  FOREIGN KEY (post_id)  REFERENCES post(id)
);

-- ------------------------------------------------------------
-- Tabela: curtidas
-- Registra qual usuário curtiu qual post OU comentário.
-- Apenas uma das colunas (post_id ou comentario_id) será
-- preenchida por vez — a outra ficará NULL.
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS curtidas (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    autor_id        BIGINT NOT NULL,
    post_id         BIGINT NULL,         -- preenchido quando for curtida em post
    comentario_id   BIGINT NULL,         -- preenchido quando for curtida em comentário
    PRIMARY KEY (id),
    CONSTRAINT fk_curtidas_autor      FOREIGN KEY (autor_id)      REFERENCES usuario(id),
    CONSTRAINT fk_curtidas_post       FOREIGN KEY (post_id)       REFERENCES post(id),
    CONSTRAINT fk_curtidas_comentario FOREIGN KEY (comentario_id) REFERENCES comentario(id),

    -- Garante que o mesmo usuário não curta o mesmo post duas vezes
    CONSTRAINT uq_curtida_post       UNIQUE (autor_id, post_id),
    -- Garante que o mesmo usuário não curta o mesmo comentário duas vezes
    CONSTRAINT uq_curtida_comentario UNIQUE (autor_id, comentario_id)
);
