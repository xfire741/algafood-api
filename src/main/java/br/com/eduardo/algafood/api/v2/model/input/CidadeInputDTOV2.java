package br.com.eduardo.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("CidadeInput")
public class CidadeInputDTOV2 {

	@ApiModelProperty(example = "Uberlândia", required = true)
	@NotBlank
	private String nomeCidade;
	
	@ApiModelProperty(example = "Uberlândia", required = true)
	@NotNull
	private Long idEstado;
	

	
}
