ALTER TABLE produto ADD CONSTRAINT fk_restaurante 
FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)