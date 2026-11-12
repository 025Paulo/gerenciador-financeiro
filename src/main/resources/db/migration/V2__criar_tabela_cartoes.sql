CREATE TABLE cartoes (
                         id BIGSERIAL PRIMARY KEY,
                         usuario_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                         nome VARCHAR(80) NOT NULL,
                         bandeira VARCHAR(20) NOT NULL,
                         ultimos_digitos VARCHAR(4),
                         limite NUMERIC(12,2) NOT NULL CHECK (limite >= 0),
                         dia_fechamento INTEGER NOT NULL CHECK (dia_fechamento BETWEEN 1 AND 31),
                         dia_vencimento INTEGER NOT NULL CHECK (dia_vencimento BETWEEN 1 AND 31),
                         criado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_cartoes_usuario ON cartoes(usuario_id);