CREATE TABLE estado(
	id BIGINT  AUTO_INCREMENT NOT NULL,
	nome VARCHAR(80) NOT NULL,
	CONSTRAINT pk_cidade PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;

ALTER TABLE cidade DROP COLUMN nome_estado;

ALTER TABLE cidade CHANGE COLUMN nome_cidade nome VARCHAR(80) NOT NULL;

ALTER TABLE cidade ADD COLUMN estado_id BIGINT NOT NULL;

ALTER TABLE cidade ADD constraint fk_cidade_estado
FOREIGN KEY (estado_id) REFERENCES estado(id);