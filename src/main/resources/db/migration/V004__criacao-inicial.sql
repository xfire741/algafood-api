CREATE TABLE forma_pagamento(
	id BIGINT  AUTO_INCREMENT NOT NULL,
	descricao VARCHAR(60) NOT NULL,
	CONSTRAINT pk_forma_pagamento PRIMARY KEY (id)

) ENGINE=InnoDB default charset=utf8;
