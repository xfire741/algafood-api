package br.com.eduardo.algafood.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.eduardo.algafood.api.model.view.RestauranteView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTO {
	
	@ApiModelProperty(example = "1")
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	
	private Boolean aberto;
	private Boolean ativo;
	private EnderecoDTO endereco;

}
