CREATE TABLE produto(
	id BIGINT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(60) NOT NULL,
	descricao VARCHAR(60) NOT NULL,
	preco DECIMAL(19,2) NOT NULL,
	ativo BIT NOT NULL,
	restaurante_id BIGINT NOT NULL,
	CONSTRAINT pk_produto PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;
