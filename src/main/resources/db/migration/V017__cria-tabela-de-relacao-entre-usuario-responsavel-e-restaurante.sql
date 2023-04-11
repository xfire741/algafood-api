CREATE TABLE restaurante_usuario_responsavel (

	restaurante_id BIGINT,
	usuario_id BIGINT,
	CONSTRAINT fk_restaurante_usuario FOREIGN KEY (restaurante_id)
	REFERENCES restaurante(id),
	CONSTRAINT fk_usuario_restaurante FOREIGN KEY (usuario_id)
	REFERENCES usuario(id),
	
	primary key (restaurante_id, usuario_id)
	
) ENGINE=InnoDB default charset=utf8;
