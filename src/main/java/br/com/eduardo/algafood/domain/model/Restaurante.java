package br.com.eduardo.algafood.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	
	@Column(length = 30)
	private String nome;
	
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;	
	
}
