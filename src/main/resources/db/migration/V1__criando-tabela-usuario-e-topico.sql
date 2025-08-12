CREATE TABLE usuario (
                         id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL
);

CREATE TABLE topico (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        titulo VARCHAR(255) NOT NULL,
                        mensagem TEXT NOT NULL,
                        data_criacao TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(50) NOT NULL,
                        autor_id BIGINT NOT NULL,
                        curso_id BIGINT NOT NULL,
                        CONSTRAINT uq_topico_titulo_mensagem UNIQUE (titulo, mensagem),
                        CONSTRAINT fk_topico_autor
                            FOREIGN KEY(autor_id)
                                REFERENCES usuario(id)
                                ON DELETE CASCADE
);