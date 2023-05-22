package br.com.eduardo.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.eduardo.algafood.api.model.view.RestauranteView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhaDTO")
@Setter
@Getter
public class CozinhaDTO {

	@ApiModelProperty(example = "1")
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@ApiModelProperty(example = "Brasileira")
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
}
