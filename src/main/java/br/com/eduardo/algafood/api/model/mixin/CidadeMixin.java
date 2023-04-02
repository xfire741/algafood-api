package br.com.eduardo.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.eduardo.algafood.domain.model.Estado;

public class CidadeMixin {

	@JsonIgnoreProperties(value = "nome")
	private Estado estado;
	
}
