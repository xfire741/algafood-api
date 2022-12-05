CREATE TABLE permissao(
	id BIGINT  AUTO_INCREMENT NOT NULL,
	nome VARCHAR(60) NOT NULL,
	descricao VARCHAR(60) NOT NULL,
	CONSTRAINT pk_permissao PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;
