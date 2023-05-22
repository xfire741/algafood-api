package br.com.eduardo.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoInputDTO {

	@ApiModelProperty(example = "Cartão de crédito")
	@NotBlank
	private String descricao;
	
}
