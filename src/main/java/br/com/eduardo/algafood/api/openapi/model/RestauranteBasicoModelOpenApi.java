package br.com.eduardo.algafood.api.openapi.model;

import java.math.BigDecimal;

import br.com.eduardo.algafood.api.model.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteBasicoModel")
@Setter
@Getter
public class RestauranteBasicoModelOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;

	@ApiModelProperty(example = "12.00")
	private BigDecimal taxaFrete;
	
	private CozinhaDTO cozinha;
	
}
