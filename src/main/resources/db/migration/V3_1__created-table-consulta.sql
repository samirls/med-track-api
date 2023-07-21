CREATE TABLE consulta (
    id BIGSERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    data_consulta TIMESTAMP NOT NULL,
    prontuario_id BIGINT NOT NULL REFERENCES prontuario (id)
);

ALTER TABLE prontuario
DROP COLUMN consulta;

ALTER TABLE prontuario
ADD COLUMN consulta_id BIGINT REFERENCES consulta (id);