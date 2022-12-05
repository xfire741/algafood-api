CREATE TABLE usuario_grupo(
	usuario_id BIGINT,
	grupo_id BIGINT,
	CONSTRAINT fk_usuario FOREIGN KEY (usuario_id)
    REFERENCES usuario(id),
	CONSTRAINT fk_grupo_usuario FOREIGN KEY (grupo_id)
    REFERENCES grupo(id)
) ENGINE=InnoDB default charset=utf8;
