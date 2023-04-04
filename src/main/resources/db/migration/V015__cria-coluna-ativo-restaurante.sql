ALTER TABLE restaurante ADD ativo TINYINT(1) NOT NULL;
UPDATE restaurante SET ativo = true;
