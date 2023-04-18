package br.com.eduardo.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.eduardo.algafood.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaDTO {

	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
}
