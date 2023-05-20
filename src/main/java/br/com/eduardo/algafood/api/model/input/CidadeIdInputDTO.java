package br.com.eduardo.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeIdInputDTO {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;
	
	
}
