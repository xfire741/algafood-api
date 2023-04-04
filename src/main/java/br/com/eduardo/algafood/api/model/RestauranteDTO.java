package br.com.eduardo.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTO {
	
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinha;
	private Boolean ativo;

}
