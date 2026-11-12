CREATE TABLE categorias (
                            id BIGSERIAL PRIMARY KEY,
                            usuario_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                            nome VARCHAR(60) NOT NULL,
                            cor VARCHAR(7) NOT NULL DEFAULT '#6B7280',
                            criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
                            CONSTRAINT uk_categorias_usuario_nome UNIQUE (usuario_id, nome)
);

CREATE INDEX idx_categorias_usuario ON categorias(usuario_id);

-- categorias padrão para usuários que já existem
INSERT INTO categorias (usuario_id, nome, cor)
SELECT u.id, c.nome, c.cor
FROM usuarios u
         CROSS JOIN (VALUES
                         ('Alimentação', '#F97316'),
                         ('Transporte',  '#3B82F6'),
                         ('Lazer',       '#A855F7'),
                         ('Saúde',       '#10B981'),
                         ('Assinaturas', '#EF4444'),
                         ('Compras',     '#EAB308'),
                         ('Outros',      '#6B7280')
) AS c(nome, cor);