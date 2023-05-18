package br.com.eduardo.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInputDTO {

	@ApiModelProperty(example = "Uberlândia")
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoIdInput estado;
	
}
