create table restaurante (
id bigint not null auto_increment,
nome varchar(30) not null, 
taxa_frete decimal(19,2) not null, 
cozinha_id bigint not null, 
endereco_cidade_id bigint,
endereco_bairro varchar(60), 
endereco_cep varchar(60), 
endereco_complemento varchar(60), 
endereco_logradouro varchar(60), 
endereco_numero varchar(60), 
data_atualizacao datetime not null, 
data_cadastro datetime not null,
CONSTRAINT pk_restaurante PRIMARY KEY (id),
CONSTRAINT fk_cozinha FOREIGN KEY (cozinha_id)
REFERENCES cozinha(id)
) engine=InnoDB charset=utf8;