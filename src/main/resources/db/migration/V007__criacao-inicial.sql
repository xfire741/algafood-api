CREATE TABLE grupo_permissao(
	grupo_id BIGINT,
	permissao_id BIGINT,
	CONSTRAINT fk_grupo FOREIGN KEY (grupo_id)
    REFERENCES grupo(id),
    CONSTRAINT fk_permissao FOREIGN KEY (permissao_id)
    REFERENCES permissao(id)

) ENGINE=InnoDB default charset=utf8;
