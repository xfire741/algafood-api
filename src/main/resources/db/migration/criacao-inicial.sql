CREATE TABLE forma_pagamento(
	id BIGINT  AUTO_INCREMENT NOT NULL,
	descricao VARCHAR(60) NOT NULL,
	CONSTRAINT pk_forma_pagamento PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;

CREATE TABLE grupo(
	id BIGINT  AUTO_INCREMENT NOT NULL,
	nome VARCHAR(60) NOT NULL,
	CONSTRAINT pk_grupo PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;

CREATE TABLE permissao(
	id BIGINT  AUTO_INCREMENT NOT NULL,
	nome VARCHAR(60) NOT NULL,
	descricao VARCHAR(60) NOT NULL,
	CONSTRAINT pk_permissao PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;

CREATE TABLE grupo_permissao(
	grupo_id BIGINT,
	permissao_id BIGINT,
	CONSTRAINT fk_grupo FOREIGN KEY (grupo_id)
    REFERENCES grupo(id),
    CONSTRAINT fk_permissao FOREIGN KEY (permissao_id)
    REFERENCES permissao(id)

) ENGINE=InnoDB default charset=utf8;

CREATE TABLE produto(
	id BIGINT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(60) NOT NULL,
	descricao VARCHAR(60) NOT NULL,
	preco DECIMAL NOT NULL,
	ativo BOOLEAN NOT NULL,
	restaurante_id NOT NULL,
	CONSTRAINT pk_produto PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;

CREATE TABLE usuario(
	id BIGINT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(60) NOT NULL,
	email VARCHAR(60) NOT NULL,
	senha VARCHAR(20) NOT NULL,
	data_cadastro DATETIME,
	CONSTRAINT pk_usuario PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;