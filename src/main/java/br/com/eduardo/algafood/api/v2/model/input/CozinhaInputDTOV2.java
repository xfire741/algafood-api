package br.com.eduardo.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInputDTOV2 {

	@ApiModelProperty(example = "Brasileira", required = true)
	@NotBlank
	private String nomeCozinha;
	
}
