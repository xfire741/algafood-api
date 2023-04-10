ALTER TABLE restaurante ADD aberto TINYINT(1) NOT NULL;
UPDATE restaurante SET aberto = true;
