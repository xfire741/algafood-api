CREATE TABLE restaurante_forma_pagamento(
	restaurante_id BIGINT,
	forma_pagamento_id BIGINT,
	CONSTRAINT fk_forma_pagamento FOREIGN KEY (forma_pagamento_id)
    REFERENCES forma_pagamento(id)
) ENGINE=InnoDB default charset=utf8;

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_restaurante_formapagamento
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);