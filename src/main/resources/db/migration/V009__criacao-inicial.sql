CREATE TABLE usuario(
	id BIGINT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(60) NOT NULL,
	email VARCHAR(60) NOT NULL,
	senha VARCHAR(20) NOT NULL,
	data_cadastro DATETIME,
	CONSTRAINT pk_usuario PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;
