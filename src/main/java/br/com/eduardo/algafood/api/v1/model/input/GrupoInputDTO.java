package br.com.eduardo.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoInputDTO {

	@ApiModelProperty(example = "Gerente")
	@NotBlank
	private String nome;
	
}
